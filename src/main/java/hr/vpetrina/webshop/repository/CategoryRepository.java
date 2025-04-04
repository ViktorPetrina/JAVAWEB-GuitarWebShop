package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.GuitarCategory;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<GuitarCategory> getAll();
    Optional<GuitarCategory> getById(Integer id);
    GuitarCategory insert(GuitarCategory item);
    Optional<GuitarCategory> update(Integer id, GuitarCategory item);
    void delete(Integer id);
}
