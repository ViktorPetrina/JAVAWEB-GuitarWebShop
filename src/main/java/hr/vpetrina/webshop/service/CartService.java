package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.CartItem;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems(HttpSession session);
    void updateCart(HttpSession session, Integer id, Integer quantity);
    void removeCartItem(HttpSession session, Integer id);
    void removeAllCartItems(HttpSession session);
    Double calculateTotal(List<CartItem> items);
    void addCartItem(HttpSession session, Integer id, Integer quantity);
}
