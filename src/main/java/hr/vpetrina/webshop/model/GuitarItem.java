package hr.vpetrina.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public GuitarItem() { /* empty because of builder pattern */ }

    public void setItemData(String title, String description, Double price, String imageUrl) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setSpecifications(String body, String neck, String pickups, GuitarCategory category) {
        this.body = body;
        this.neck = neck;
        this.pickups = pickups;
        this.category = category;
    }
}
