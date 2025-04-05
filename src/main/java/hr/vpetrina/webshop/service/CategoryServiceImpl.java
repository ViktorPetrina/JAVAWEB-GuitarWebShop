package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.repository.CategoryRepository;
import hr.vpetrina.webshop.repository.GuitarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final GuitarRepository guitarRepository;

    @Override
    public List<GuitarCategory> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public Optional<GuitarCategory> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public GuitarCategory insert(GuitarCategory item) {
        categoryRepository.insert(item);
        return item;
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.delete(id);
    }

    @Override
    public boolean hasGuitars(Integer id) {
        AtomicReference<Boolean> has = new AtomicReference<>(false);
        guitarRepository.getAll()
                .stream()
                .filter(guitar -> guitar.getCategoryId().equals(id))
                .findFirst()
                .ifPresent(guitar -> has.set(true));

        return has.get();
    }
}
