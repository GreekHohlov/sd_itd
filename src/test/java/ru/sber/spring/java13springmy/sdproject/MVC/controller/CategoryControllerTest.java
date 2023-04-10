package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;

import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class CategoryControllerTest extends CommonTestMVC {
    @Test
    @DisplayName("Просмотр всех категорий через MVC контроллер, тестирование '/category'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех категорий через MVC начат");
        mvc.perform(get("/category")
//                        .param("page", "1")
//                        .param("size", "5")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("category/viewAllCategory"))
                .andExpect(model().attributeExists("category"))
                .andReturn();
    }

    @Test
    @DisplayName("Создание категории через MVC контроллер, тестирование 'category/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию категории через MVC начат успешно");
        CategoryDTO categoryDTO = new CategoryDTO("MVC_TestCategoryName",
                1L,
                null,
                new HashSet<>(),
                new HashSet<>());

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
     }

    @Override
    protected void delete() throws Exception {

    }


    @Test
    @DisplayName("Удаление группы через MVC контроллер, тестирование 'groups/deleteSoft'")
    @Order(2)
//    @Override
    protected void deleteSoft() throws Exception {

    }
}
