package hr.vpetrina.webshop.model;

import hr.vpetrina.webshop.dto.GuitarItemDto;
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
