package ru.sber.spring.java13springmy.sdproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;




@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j

public class GroupControllerTest extends CommonTest {


    @Test
    @Order(0)
    protected void listAll() throws Exception {
        log.info("Тест по просмотра всех групп через MVC начат успешно");
//        String result = mvc.perform(
//                        get("/groups")
//                                .headers(headers)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(jsonPath("$.*", hasSize(greaterThan(0))))
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        List<AuthorDTO> authorDTOS = objectMapper.readValue(result, new TypeReference<List<AuthorDTO>>() {});
//        authorDTOS.forEach(a -> log.info(a.toString()));
//        log.info("Тест по просмотра всех авторов через REST закончен успешно");
    }

    @Override
    protected void createObject() throws Exception {

    }

    @Override
    protected void updateObject() throws Exception {

    }

    @Override
    protected void deleteObject() throws Exception {

    }
}
