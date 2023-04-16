package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.service.CategoryService;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@Rollback(value = false)
@Slf4j
public class CategoryControllerTest extends CommonTestMVC {
    private final CategoryDTO categoryDTO = new CategoryDTO("MVC_TestCategoryName",1L,
            null, new HashSet<>(), new HashSet<>());
    @Autowired
    private CategoryService categoryService;
    @Test
    @DisplayName("Просмотр всех категорий через MVC контроллер, тестирование '/category'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех категорий через MVC начат");
        mvc.perform(get("/category")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("category/viewAllCategory"))
                .andExpect(model().attributeExists("category"))
                .andReturn();
        log.info("Тест по выбору всех категорий завершен успешно");
    }

    @Test
    @DisplayName("Создание категории через MVC контроллер, тестирование 'category/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию категории через MVC начат успешно");
        mvc.perform(post("/category/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("categoryForm", categoryDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category"))
                .andExpect(redirectedUrlTemplate("/category"))
                .andExpect(redirectedUrl("/category"));
        log.info("Тест по созданию группы через MVC закончен успешно");
    }


    @Override
    protected void update() throws Exception {
        throw new UnsupportedOperationException("Метод обновления категории не поддеживается");
     }



    @Test
    @DisplayName("Софт удаление группы через MVC контроллер, тестирование 'category/deleteSoft'")
    @Order(2)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void deleteSoft() throws Exception {
        log.info("Тест по soft удалению категории через MVC начат успешно");
        CategoryDTO foundCategoryForDelete = categoryService.findByName(categoryDTO.getNameCategory());
        mvc.perform(get("/category/deleteSoft/{id}", foundCategoryForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category"))
                .andExpect(redirectedUrl("/category"));
        CategoryDTO deletedCategory = categoryService.getOne(foundCategoryForDelete.getId());
        assertTrue (deletedCategory.isDeleted());
        log.info("Тест soft удаления категории завершен успешно");
    }
    @Test
    @DisplayName("Восстановление категории через MVC контроллер, тестирование 'category/restore'")
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void restore() throws Exception {
        log.info("Тест по восстановлению категории через MVC начат успешно");
        CategoryDTO foundCategoryForDelete = categoryService.findByName(categoryDTO.getNameCategory());
        mvc.perform(get("/category/restore/{id}", foundCategoryForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category"))
                .andExpect(redirectedUrl("/category"));
        CategoryDTO deletedCategory = categoryService.getOne(foundCategoryForDelete.getId());
        assertFalse (deletedCategory.isDeleted());
        log.info("Тест восстановления категории завершен успешно");
    }
    @Test
    @DisplayName("Удаление категории через MVC контроллер, тестирование 'category/delete'")
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void delete() throws Exception {
        log.info("Тест по hard удалению категории через MVC начат успешно");
        CategoryDTO foundCategoryForDelete = categoryService.findByName(categoryDTO.getNameCategory());
        mvc.perform(get("/category/delete/{id}", foundCategoryForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/category"))
                .andExpect(redirectedUrl("/category"));
        assert (categoryService.findByName(categoryDTO.getNameCategory()) == null);
        log.info("Тест hard удаления категории завершен успешно");
    }
}
