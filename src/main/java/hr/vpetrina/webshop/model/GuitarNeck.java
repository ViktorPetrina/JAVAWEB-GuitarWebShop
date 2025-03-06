package hr.vpetrina.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuitarNeck {
    private String shape;
    private Double nutWidth;
    private String material;
    private Integer numberOfFrets;
}
