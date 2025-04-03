package hr.vpetrina.webshop.controller.mvc;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.base.rest.PayPalRESTException;
import hr.vpetrina.webshop.service.CartService;
import hr.vpetrina.webshop.service.PaypalService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public String showCheckout(HttpSession session, Model model) {
        var items = cartService.getCartItems(session);
        model.addAttribute("totalPrice", cartService.calculateTotal(items));
        return CHECKOUT;
    }

    @PostMapping
    public RedirectView checkout(HttpSession session, Model model, @RequestParam("paymentMethod") String paymentMethod) {
        var items = cartService.getCartItems(session);
        var totalPrice = cartService.calculateTotal(items);

        model.addAttribute("totalPrice", totalPrice);

        if ("paypal".equals(paymentMethod)) {

            var address = new ShippingAddress();
            address.setLine1("123 Main Street"); // First line of address (required)
            address.setLine2("Apt 4B"); // Second line of address (optional)
            address.setCity("New York"); // City (required)
            address.setState("NY"); // State (required)
            address.setPostalCode("10001"); // Postal Code (required)
            address.setCountryCode("US"); // Country Code (required)

            try {
                Payment payment = paypalService.createPayment(
                        100.0,
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
