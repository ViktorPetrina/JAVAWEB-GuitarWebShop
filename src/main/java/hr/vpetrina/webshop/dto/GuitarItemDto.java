package hr.vpetrina.webshop.dto;

import hr.vpetrina.webshop.model.GuitarCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class GuitarItemDto implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String body;
    private String neck;
    private String pickups;
    private GuitarCategory category;
    private String imageUrl;
}
