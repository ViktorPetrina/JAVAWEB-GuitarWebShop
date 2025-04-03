package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.model.CartItem;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private static final String CART_SESSION_KEY = "cartItems";
    
    private final GuitarService guitarService;

    @Override
    public List<CartItem> getCartItems(HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute(CART_SESSION_KEY);
        return (cartItems != null) ? cartItems : new ArrayList<>();
    }

    @Override
    public void updateCart(HttpSession session, Integer id, Integer quantity) {
        List<CartItem> cartItems = getCartItems(session);
        for (CartItem item : cartItems) {
            if (item.getGuitarId().equals(id)) {
                item.setQuantity(quantity);
                session.setAttribute(CART_SESSION_KEY, cartItems);
                return;
            }
        }
    }

    @Override
    public void removeCartItem(HttpSession session, Integer id) {
        List<CartItem> cartItems = getCartItems(session);
        cartItems.removeIf(item -> item.getGuitarId().equals(id));
        session.setAttribute(CART_SESSION_KEY, cartItems);
    }

    @Override
    public Double calculateTotal(List<CartItem> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public void addCartItem(HttpSession session, Integer id, Integer quantity) {
        List<CartItem> cartItems = getCartItems(session);

        for (CartItem item : cartItems) {
            if (item.getGuitarId().equals(id)) {
                item.setQuantity(item.getQuantity() + quantity);
                session.setAttribute(CART_SESSION_KEY, cartItems);
                return;
            }
        }

        Optional<GuitarItemDto> byId = guitarService.getById(id);
        if (byId.isPresent()) {
            CartItem newItem = new CartItem(id, quantity, getPriceByGuitarId(id), byId.get());
            cartItems.add(newItem);
            session.setAttribute(CART_SESSION_KEY, cartItems);
        }
    }

    private double getPriceByGuitarId(int id) {
        Optional<GuitarItemDto> guitar = guitarService.getById(id);
        if (guitar.isPresent()) {
            return guitar.get().getPrice();
        }
        return 0;
    }
}
