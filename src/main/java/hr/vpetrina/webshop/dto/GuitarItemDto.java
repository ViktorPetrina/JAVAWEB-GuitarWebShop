package hr.vpetrina.webshop.dto;

import hr.vpetrina.webshop.model.GuitarBody;
import hr.vpetrina.webshop.model.GuitarNeck;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuitarItemDto {
    private String title;
    private String description;
    private Double price;
    private GuitarBody body;
    private GuitarNeck neck;
    private String pickups;
}
