package hr.vpetrina.webshop.controller.mvc;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hr.vpetrina.webshop.model.CartItem;
import hr.vpetrina.webshop.model.PaymentType;
import hr.vpetrina.webshop.model.User;
import hr.vpetrina.webshop.model.UserPurchaseDto;
import hr.vpetrina.webshop.service.CartService;
import hr.vpetrina.webshop.service.PaypalService;
import hr.vpetrina.webshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("GuitarStore/paypal")
public class PaypalController {

    private final PaypalService paypalService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            HttpSession session
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {

                List<CartItem> cartItems = cartService.getCartItems(session);
                User user = (User) session.getAttribute("user");

                cartItems.forEach(item -> userService.insertPurchase(new UserPurchaseDto(
                        item.getQuantity(),
                        item.getPrice(),
                        new Date(System.currentTimeMillis()),
                        PaymentType.PAYPAL,
                        item.getGuitar(),
                        user
                )));

                cartItems.forEach(item -> cartService.removeCartItem(session, item.getGuitarId()));

                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }

        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }
}
