package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.CategoryMapper;
import ru.sber.spring.java13springmy.sdproject.model.Category;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService extends GenericService<Category, CategoryDTO> {
    protected CategoryService(CategoryRepository categoryRepository,
                              CategoryMapper categoryMapper) {
        super(categoryRepository, categoryMapper);
    }

    public List<String> getName(List<CategoryDTO> categoryDTO) {
        List<String> names = new ArrayList<>();
        for (CategoryDTO name : categoryDTO) {
            names.add(name.getNameCategory());
        }
        return names;
    }

    public void deleteSoft(Long id) throws MyDeleteException {
        Category category = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Категории с заданным ID=" + id + " не существует"));
        markAsDeleted(category);
        repository.save(category);
    }

    public void restore(Long objectId) {
        Category category = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Категории с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(category);
        repository.save(category);
    }
}
