package hr.vpetrina.webshop.controller.mvc;

import hr.vpetrina.webshop.model.CartItem;
import hr.vpetrina.webshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("GuitarStore/cart")
public class CartController {

    public static final String REDIRECT_TO_CART = "redirect:/GuitarStore/cart/show";

    private final CartService cartService;

    @GetMapping("/show")
    public String showCart(Model model, HttpSession session) {
        List<CartItem> cartItems = cartService.getCartItems(session);
        double totalPrice = cartService.calculateTotal(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam int id, @RequestParam int quantity, HttpSession session) {
        cartService.updateCart(session, id, quantity);
        return REDIRECT_TO_CART;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam int id, @RequestParam int quantity, HttpSession session) {
        cartService.addCartItem(session, id, quantity);
        return REDIRECT_TO_CART;
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam int id, HttpSession session) {
        cartService.removeCartItem(session, id);
        return REDIRECT_TO_CART;
    }
}