package hr.vpetrina.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuitarItem {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String body;
    private String neck;
    private String pickups;
    private GuitarCategory category;
    private String imageUrl;

    public GuitarItem() {}

    public GuitarItem(
            String title,
            String description,
            Double price,
            String body,
            String neck,
            String pickups,
            GuitarCategory category,
            String imageUrl
    ) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.body = body;
        this.neck = neck;
        this.pickups = pickups;
        this.category = category;
        this.imageUrl = imageUrl;
    }
}
