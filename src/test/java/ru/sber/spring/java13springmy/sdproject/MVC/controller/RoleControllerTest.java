package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@Rollback(value = false)
@Slf4j
public class RoleControllerTest extends CommonTestMVC {
    @Test
    @DisplayName("Просмотр всех ролей через MVC контроллер, тестирование '/role'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех ролей через MVC начат");
        mvc.perform(get("/role")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("role/viewAllRole"))
                .andExpect(model().attributeExists("roles"))
                .andReturn();
    }

    @Override
    protected void create() throws Exception {
        throw new UnsupportedOperationException("Метод создания ролей не поддеживается");
    }

    @Override
    protected void update() throws Exception {
        throw new UnsupportedOperationException("Метод обновления ролей не поддеживается");
    }

    @Override
    protected void delete() throws Exception {
        throw new UnsupportedOperationException("Метод удаления категории не поддеживается");
    }
}
