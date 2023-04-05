package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.model.Category;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface CategoryTestData {
    CategoryDTO CATEGORY_DTO1 = new CategoryDTO("nameCategory1",
            null,
            null,
            new HashSet<>(),
            new HashSet<>());

    CategoryDTO CATEGORY_DTO2 = new CategoryDTO("nameCategory2",
            1L,
            CategoryTestData.CATEGORY_1,
            new HashSet<>(),
            new HashSet<>());
    CategoryDTO CATEGORY_DTO3 = new CategoryDTO("nameCategory3",
            1L,
            CategoryTestData.CATEGORY_1,
            new HashSet<>(),
            new HashSet<>());

    List<CategoryDTO> CATEGORY_DTO_LIST = Arrays.asList(CATEGORY_DTO1, CATEGORY_DTO2, CATEGORY_DTO3);

    Category CATEGORY_1 = new Category("nameCategory1",
            new HashSet<>(),
            null,
            new HashSet<>());
    Category CATEGORY_2 = new Category("nameCategory2",
            new HashSet<>(),
            CategoryTestData.CATEGORY_1,
            new HashSet<>());
    Category CATEGORY_3 = new Category("nameCategory3",
            new HashSet<>(),
            CategoryTestData.CATEGORY_1,
            new HashSet<>());

    List<Category> CATEGORIES_LIST = Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3);
}

