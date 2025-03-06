package hr.vpetrina.webshop.repository;

import hr.vpetrina.webshop.model.GuitarItem;

import java.util.List;
import java.util.Optional;

public interface GuitarRepository {
    List<GuitarItem> getAll();
    Optional<GuitarItem> getById(Integer id);
    GuitarItem insert(GuitarItem item);
    Optional<GuitarItem> update(Integer id, GuitarItem item);
    void delete(Integer id);
    List<GuitarItem> filterByName(String query);
}
