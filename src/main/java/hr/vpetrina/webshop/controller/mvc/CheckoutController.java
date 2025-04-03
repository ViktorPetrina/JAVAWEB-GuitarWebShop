package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/checkout")
public class CheckoutController {

    private static final String CHECKOUT = "checkout";

    private final CartService cartService;

    @GetMapping
    public String showCheckout(HttpSession session, Model model) {
        var items = cartService.getCartItems(session);
        model.addAttribute("totalPrice", cartService.calculateTotal(items));
        return CHECKOUT;
    }

    @PostMapping
    public String checkout(
            HttpSession session, Model model,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam(value = "paypalEmail", required = false) String paypalEmail
    ) {
        var items = cartService.getCartItems(session);
        model.addAttribute("totalPrice", cartService.calculateTotal(items));

        if ("paypal".equals(paymentMethod)) {
            if (paypalEmail != null && !paypalEmail.isEmpty()) {
                // Process payment using PayPal
                // E.g., call PayPal API to complete the payment
                model.addAttribute("message", "Payment completed via PayPal!");
                return "paypal";
            } else {
                model.addAttribute("error", "PayPal email is required for this payment method.");
                return CHECKOUT;
            }
        } else if ("payment-on-delivery".equals(paymentMethod)) {
            // Handle payment on delivery
            // Usually, no further action is required apart from confirming the order
            model.addAttribute("message", "Order confirmed! You'll pay upon delivery.");
        } else {
            model.addAttribute("error", "Invalid payment method selected.");
            return CHECKOUT;
        }

        // Clear the cart after successful checkout

        return "checkout-success"; // Redirect or show success page
    }
}
