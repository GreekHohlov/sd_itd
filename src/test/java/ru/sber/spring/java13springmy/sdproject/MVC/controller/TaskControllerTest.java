package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sber.spring.java13springmy.sdproject.model.Priority.HIGH;
import static ru.sber.spring.java13springmy.sdproject.model.StatusTask.OPEN;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j

public class TaskControllerTest extends CommonTest {
    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @Order(0)
    @Override
    protected void listAll() throws Exception {
        log.info("Тест просмотра всех заявок через MVC начат успешно");
        String result = mvc.perform(get("/task")
//                        .headers(super.headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        log.info("Тест по просмотру всех пользователей через MVC пройден успешно");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "EXECUTOR")
    @Order(1)
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по созданию заявки через MVC начат успешно");
        TaskDTO taskDTO = new TaskDTO("Тест через MVC", 20L, HIGH, 1L, "Проблема с тестированием", LocalDateTime.now(), LocalDateTime.now().plusHours(5), null, 1L, 1L, OPEN, null, null, null);
        TaskDTO result = objectMapper.readValue(mvc.perform(post("/task/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                                .headers(headers)
                                .contentType(asJsonString(taskDTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                TaskDTO.class);
        log.info("Тест по созданию заявки через MVC завершен успешно" + result);
    }

    @Override
    protected void updateObject() throws Exception {

    }

    @Override
    protected void deleteObject() throws Exception {

    }
}
