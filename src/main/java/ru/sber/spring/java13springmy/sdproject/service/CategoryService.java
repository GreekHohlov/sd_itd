package ru.sber.spring.java13springmy.sdproject.service;

import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.CategoryMapper;
import ru.sber.spring.java13springmy.sdproject.model.Category;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;

public class CategoryService extends GenericService<Category, CategoryDTO> {
    protected CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        super(categoryRepository, categoryMapper);
    }
}
