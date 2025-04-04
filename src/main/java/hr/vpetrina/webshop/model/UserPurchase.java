package hr.vpetrina.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserPurchase {
    private Integer id;
    private Integer quantity;
    private Double totalPrice;
    private Date date;
    private PaymentType paymentType;
    private Integer guitarId;
    private Integer userId;

    public UserPurchase() {}

    public UserPurchase(Integer quantity, Double totalPrice, Date date, PaymentType paymentType, Integer guitarId, Integer userId) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
        this.paymentType = paymentType;
        this.guitarId = guitarId;
        this.userId = userId;
    }
}
