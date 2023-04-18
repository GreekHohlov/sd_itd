package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.CategoryTestData;
import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.CategoryMapper;
import ru.sber.spring.java13springmy.sdproject.model.Category;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceTest extends GenericTest<Category, CategoryDTO> {
    public CategoryServiceTest() {
        super();
        repository = Mockito.mock(CategoryRepository.class);
        mapper = Mockito.mock(CategoryMapper.class);
        service = new CategoryService((CategoryRepository) repository, (CategoryMapper) mapper);
    }

    @Test
    @Order(1)
    @Override
    protected void listAll() {
        Mockito.when(repository.findAll()).thenReturn(CategoryTestData.CATEGORIES_LIST);
        Mockito.when(mapper.toDTOs(CategoryTestData.CATEGORIES_LIST)).thenReturn(CategoryTestData.CATEGORY_DTO_LIST);
        List<CategoryDTO> categoryDTOS = service.listAll();
        log.info("Testing getAll(): " + categoryDTOS);
        assertEquals(CategoryTestData.CATEGORIES_LIST.size(), categoryDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(CategoryTestData.CATEGORY_1));
        Mockito.when(mapper.toDto(CategoryTestData.CATEGORY_1)).thenReturn(CategoryTestData.CATEGORY_DTO1);
        CategoryDTO categoryDTO = service.getOne(1L);
        log.info("Testing getOne(): " + categoryDTO);
        assertEquals(CategoryTestData.CATEGORY_DTO1, categoryDTO);
    }

    @Test
    @Order(3)
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(CategoryTestData.CATEGORY_DTO1)).thenReturn(CategoryTestData.CATEGORY_1);
        Mockito.when(mapper.toDto(CategoryTestData.CATEGORY_1)).thenReturn(CategoryTestData.CATEGORY_DTO1);
        Mockito.when(repository.save(CategoryTestData.CATEGORY_1)).thenReturn(CategoryTestData.CATEGORY_1);
        CategoryDTO categoryDTO = service.create(CategoryTestData.CATEGORY_DTO1);
        log.info("Testing create(): " + categoryDTO);
        assertEquals(CategoryTestData.CATEGORY_DTO1, categoryDTO);
    }

    @Test
    @Order(4)
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(CategoryTestData.CATEGORY_DTO2)).thenReturn(CategoryTestData.CATEGORY_2);
        Mockito.when(mapper.toDto(CategoryTestData.CATEGORY_2)).thenReturn(CategoryTestData.CATEGORY_DTO2);
        Mockito.when(repository.save(CategoryTestData.CATEGORY_2)).thenReturn(CategoryTestData.CATEGORY_2);
        CategoryDTO categoryDTO = service.update(CategoryTestData.CATEGORY_DTO2);
        log.info("Testing create(): " + categoryDTO);
        assertEquals(CategoryTestData.CATEGORY_DTO2, categoryDTO);
    }

    @Test
    @Order(5)
    @Override
    protected void delete() throws MyDeleteException {

//        Mockito.when(((AuthorRepository) repository).checkAuthorForDeletion(1L)).thenReturn(true);
////        Mockito.when(authorRepository.checkAuthorForDeletion(2L)).thenReturn(false);
//        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
//        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
//        log.info("Testing delete() before: " + AuthorTestData.AUTHOR_1.isDeleted());
//        service.delete(1L);
//        log.info("Testing delete() after: " + AuthorTestData.AUTHOR_1.isDeleted());
//        assertTrue(AuthorTestData.AUTHOR_1.isDeleted());
    }

    @Test
    @Order(6)
    protected void restore() {
        CategoryTestData.CATEGORY_2.setDeleted(true);
        Mockito.when(repository.save(CategoryTestData.CATEGORY_2)).thenReturn(CategoryTestData.CATEGORY_2);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(CategoryTestData.CATEGORY_2));
        log.info("Testing restore() before: " + CategoryTestData.CATEGORY_2.isDeleted());
        ((CategoryService) service).restore(2L);
        log.info("Testing restore() after: " + CategoryTestData.CATEGORY_2.isDeleted());
        assertFalse(CategoryTestData.CATEGORY_2.isDeleted());
    }
}
