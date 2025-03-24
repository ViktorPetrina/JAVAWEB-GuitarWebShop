package hr.vpetrina.webshop.dto;

import hr.vpetrina.webshop.model.GuitarCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuitarItemDto {
    private String title;
    private String description;
    private Double price;
    private String body;
    private String neck;
    private String pickups;
    private GuitarCategory category;
    private String imageUrl;
}
