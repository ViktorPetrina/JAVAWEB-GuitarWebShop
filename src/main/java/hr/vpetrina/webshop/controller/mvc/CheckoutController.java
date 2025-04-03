package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/checkout")
public class CheckoutController {

    private final CartService cartService;

    @GetMapping
    public String showCheckout(HttpSession session, Model model) {
        var items = cartService.getCartItems(session);
        model.addAttribute("totalPrice", cartService.calculateTotal(items));
        return "checkout";
    }

    @PostMapping
    public String checkout() {



        return "checkout";
    }
}
