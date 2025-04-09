package hr.vpetrina.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class GuitarCategory implements Serializable {
    private Integer id;
    private String name;

    public GuitarCategory() { /* empty because of row mapper */ }
}
