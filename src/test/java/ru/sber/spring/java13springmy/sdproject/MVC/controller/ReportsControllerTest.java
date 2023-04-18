package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@Transactional
@Rollback(value = false)
@Slf4j

public class ReportsControllerTest extends CommonTestMVC {


    @Test
    @DisplayName("Просмотр всех отчетов через MVC контроллер, тестирование '/reports'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех отчетов через MVC начат");
        mvc.perform(get("/reports")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reports/report"))
                .andReturn();
    }

    @Override
    protected void create() throws Exception {
        throw new UnsupportedOperationException("Метод создания отчетов не поддеживается");
    }

    @Override
    protected void update() throws Exception {
        throw new UnsupportedOperationException("Метод обновления отчетов не поддеживается");
    }

    @Override
    protected void delete() throws Exception {
        throw new UnsupportedOperationException("Метод удаления отчетов не поддеживается");
    }

}


