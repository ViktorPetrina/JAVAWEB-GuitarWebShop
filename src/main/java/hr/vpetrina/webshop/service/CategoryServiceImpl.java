package hr.vpetrina.webshop.service;

import hr.vpetrina.webshop.model.GuitarCategory;
import hr.vpetrina.webshop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

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
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
