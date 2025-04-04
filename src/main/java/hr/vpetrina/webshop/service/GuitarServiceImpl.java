package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.dto.GuitarItemDto;
import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.model.GuitarItem;
import hr.vpetrina.webshop.repository.CategoryRepository;
import hr.vpetrina.webshop.repository.GuitarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuitarServiceImpl implements GuitarService {

    private final GuitarRepository repo;
    private final CategoryRepository categoryRepo;

    public GuitarServiceImpl(GuitarRepository repo, CategoryRepository categoryRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<GuitarItemDto> getAll() {
        return repo.getAll().stream().map(this::toDto).toList();
    }

    @Override
    public Optional<GuitarItemDto> getById(Integer id) {
        Optional<GuitarItem> guitarItemOptional =
                repo.getById(id);

        if (guitarItemOptional.isPresent()) {
            GuitarItem guitarItem = guitarItemOptional.get();
            return Optional.of(toDto(guitarItem));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public GuitarItemDto insert(GuitarItemDto item) {
        GuitarItem guitarItem =
                repo.insert(toEntity(item));
        return toDto(guitarItem);
    }

    @Override
    public Optional<GuitarItemDto> update(Integer id, GuitarItemDto item) {
        Optional<GuitarItem> guitarItemOptional =
                repo.update(id, toEntity(item));

        if(guitarItemOptional.isPresent()) {
            GuitarItem guitarItem = guitarItemOptional.get();
            return Optional.of(toDto(guitarItem));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Integer id) {
        repo.delete(id);
    }

    @Override
    public List<GuitarItemDto> filterByName(String query) {
        return repo.filterByName(query)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<GuitarItemDto> getFilteredGuitars(Integer category, String query) {
        return repo.getFilteredGuitars(category, query)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private GuitarItemDto toDto(GuitarItem item) {
        var category = categoryRepo.getById(item.getCategoryId());

        if (!category.isPresent()) {
            return null;
        }

        return new GuitarItemDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getBody(),
                item.getNeck(),
                item.getPickups(),
                category.get(),
                item.getImageUrl()
        );
    }

    private GuitarItem toEntity(GuitarItemDto dto) {
        GuitarItem item = new GuitarItem();
        item.setItemData(dto.getTitle(), dto.getDescription(), dto.getPrice(), dto.getImageUrl());
        item.setSpecifications(dto.getBody(), dto.getNeck(), dto.getPickups(), dto.getCategory().getId());
        return item;
    }
}
