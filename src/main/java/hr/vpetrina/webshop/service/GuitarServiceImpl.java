package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.model.GuitarItem;
import hr.vpetrina.webshop.repository.GuitarRepository;

import java.util.List;
import java.util.Optional;

public class GuitarServiceImpl implements GuitarService {

    private final GuitarRepository repo;

    public GuitarServiceImpl(GuitarRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<GuitarItemDto> getAll() {
        return repo.getAll().stream().map(this::toDto).toList();
    }

    @Override
    public Optional<GuitarItemDto> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public GuitarItemDto insert(GuitarItemDto item) {
        return null;
    }

    @Override
    public Optional<GuitarItemDto> update(Integer id, GuitarItemDto item) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<GuitarItemDto> filterByName(String query) {
        return List.of();
    }

    private GuitarItemDto toDto(GuitarItem item) {
        return new GuitarItemDto(
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getBody(),
                item.getNeck(),
                item.getPickups()
        );
    }

    private GuitarItem toEntity(GuitarItemDto dto) {
        return new GuitarItem(
                dto.getTitle(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getBody(),
                dto.getNeck(),
                dto.getPickups()
        );
    }
}
