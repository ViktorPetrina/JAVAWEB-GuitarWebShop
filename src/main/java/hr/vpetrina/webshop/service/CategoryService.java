package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.GuitarCategory;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<GuitarCategory> getAll();
    Optional<GuitarCategory> getById(Integer id);
    GuitarCategory insert(GuitarCategory item);
    void delete(Integer id);
}
