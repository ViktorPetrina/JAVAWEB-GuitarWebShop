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
    private GuitarBody body;
    private GuitarNeck neck;
    private String pickups;

    public GuitarItem(String title, String description, Double price, GuitarBody body, GuitarNeck neck, String pickups) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.body = body;
        this.neck = neck;
        this.pickups = pickups;
    }
}
