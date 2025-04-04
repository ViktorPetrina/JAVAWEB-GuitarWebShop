package hr.vpetrina.webshop.controller.mvc;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hr.vpetrina.webshop.model.PaymentType;
import hr.vpetrina.webshop.service.PaymentService;
import hr.vpetrina.webshop.service.PaypalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("GuitarStore/paypal")
public class PaypalController {

    private final PaypalService paypalService;
    private final PaymentService paymentService;

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            HttpSession session
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                paymentService.savePaymentHistory(session, PaymentType.PAYPAL);
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
