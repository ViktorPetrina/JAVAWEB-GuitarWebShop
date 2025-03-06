package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.dto.GuitarItemDto;

import java.util.List;
import java.util.Optional;

public interface GuitarService {
    List<GuitarItemDto> getAll();
    Optional<GuitarItemDto> getById(Integer id);
    GuitarItemDto insert(GuitarItemDto item);
    Optional<GuitarItemDto> update(Integer id, GuitarItemDto item);
    void delete(Integer id);
    List<GuitarItemDto> filterByName(String query);
}
