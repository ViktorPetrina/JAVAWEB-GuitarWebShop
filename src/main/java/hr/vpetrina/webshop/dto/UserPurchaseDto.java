package hr.vpetrina.webshop.dto;

import hr.vpetrina.webshop.model.PaymentType;
import hr.vpetrina.webshop.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
public class UserPurchaseDto {
    private Integer quantity;
    private Double totalPrice;
    private Date date;
    private PaymentType paymentType;
    private GuitarItemDto guitar;
    private User user;
}
