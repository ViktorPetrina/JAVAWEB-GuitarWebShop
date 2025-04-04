package hr.vpetrina.webshop.controller.mvc;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.PayPalRESTException;
import hr.vpetrina.webshop.service.CartService;
import hr.vpetrina.webshop.service.PaypalService;
import hr.vpetrina.webshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/checkout")
public class CheckoutController {

    public static final String CANCEL_URL = "http://localhost:8080/GuitarStore/paypal/payment/cancel";
    public static final String SUCCESS_URL = "http://localhost:8080/GuitarStore/paypal/payment/success";
    private static final String CHECKOUT = "checkout";

    private final CartService cartService;
    private final PaypalService paypalService;
    private final UserService userService;

    @GetMapping
    public String showCheckout(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        var items = cartService.getCartItems(session);

        if (!userService.isLoggedIn(request, session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Must be logged in to checkout");
            return "redirect:/GuitarStore/cart/show";
        }

        if (items.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The cart is empty");
            return "redirect:/GuitarStore/cart/show";
        }

        model.addAttribute("totalPrice", cartService.calculateTotal(items));
        return CHECKOUT;
    }

    @PostMapping
    public RedirectView checkout(
            HttpSession session,
            Model model,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("line1") String line1,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("countryCode") String countryCode
    ) {
        var items = cartService.getCartItems(session);
        var totalPrice = cartService.calculateTotal(items);

        model.addAttribute("totalPrice", totalPrice);

        if ("paypal".equals(paymentMethod)) {

            var address = new ShippingAddress();
            address.setLine1(line1);
            address.setCity(city);
            address.setState(state);
            address.setPostalCode(postalCode);
            address.setCountryCode(countryCode);

            try {
                Payment payment = paypalService.createPayment(
                        totalPrice,
                        "USD",
                        "sale",
                        CANCEL_URL,
                        SUCCESS_URL,
                        address
                );

                for (Links links : payment.getLinks()) {
                    if (links.getRel().equals("approval_url")) {
                        return new RedirectView(links.getHref());
                    }
                }

            } catch (PayPalRESTException e) {
                log.error(e.getMessage());
            }

        } else if ("payment-on-delivery".equals(paymentMethod)) {
            model.addAttribute("message", "Order confirmed! You'll pay upon delivery.");
        } else {
            model.addAttribute("error", "Invalid payment method selected.");
            return new RedirectView(CHECKOUT);
        }

        return new RedirectView("checkout-success");
    }
}
