package hr.vpetrina.webshop.model;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CartItem implements Serializable {
    private Integer guitarId;
    private Integer quantity;
    private Double price;
    private GuitarItemDto guitar;
}
