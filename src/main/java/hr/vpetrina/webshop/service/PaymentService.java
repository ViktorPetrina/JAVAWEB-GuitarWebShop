package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.CartItem;
import hr.vpetrina.webshop.model.PaymentType;
import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.dto.UserPurchaseDto;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private final CartService cartService;
    private final UserService userService;

    public void savePaymentHistory(HttpSession session, PaymentType paymentType) {
        List<CartItem> cartItems = cartService.getCartItems(session);
        User user = (User) session.getAttribute("user");

        cartItems.forEach(item -> userService.insertPurchase(new UserPurchaseDto(
                item.getQuantity(),
                item.getPrice(),
                new Date(System.currentTimeMillis()),
                paymentType,
                item.getGuitar(),
                user
        )));

        cartService.removeAllCartItems(session);
    }
}
