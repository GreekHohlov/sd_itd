package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j

public class TypeTaskControllerTest extends CommonTestMVC {


    @Test
    @DisplayName("Просмотр всех отчетов через MVC контроллер, тестирование '/reports'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех типов заявок через MVC начат");
        mvc.perform(get("/typetask")
//                        .param("page", "1")
//                        .param("size", "5")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("typetask/viewAllTypeTask"))
                .andExpect(model().attributeExists("typeTask"))
                .andReturn();
    }

    @Override
    protected void create() throws Exception {
//        log.info("Тест по созданию типа заявок через MVC начат успешно");
//        TypeTaskDTO typeTaskDTO = new TypeTaskDTO("MVC_TestTypeTaskName",
//                1L,
//                1L,
//                "description1",
//                1L,
//                new HashSet<>());
//
//        mvc.perform(post("/task/add")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .flashAttr("taskForm", taskDTO)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .with(csrf()))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/task"))
//                .andExpect(redirectedUrlTemplate("/task"))
//                .andExpect(redirectedUrl("/task"));
//        log.info("Тест по созданию заявки через MVC закончен успешно");
    }

    @Override
    protected void update() throws Exception {

    }

    @Override
    protected void delete() throws Exception {

//        log.info("Тест по удалению группы через MVC начат успешно");
//        mvc.perform(MockMvcRequestBuilders.delete("/groups/deleteSoft/{id}", 1L)
//                        //    .headers(headers)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//        GroupDTO existingGroup = objectMapper.readValue(mvc.perform(get("/groups/getOneById")
//                                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                                //       .headers(super.headers)
//                                .param("id", String.valueOf(1L))
//                                .accept(MediaType.APPLICATION_JSON_VALUE))
//                        .andExpect(status().is2xxSuccessful())
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                        .andReturn()
//                        .getResponse()
//                        .getContentAsString(),
//                GroupDTO.class);
//
//        assertTrue(existingGroup.isDeleted());
//        log.info("Тест по удалению группы через MVC завершен успешно");
//        mvc.perform(
//                        delete("/rest/authors/delete/hard/{id}", createdAuthorID)
//                                .headers(headers)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//        log.info("Данные очищены");
    }

}


