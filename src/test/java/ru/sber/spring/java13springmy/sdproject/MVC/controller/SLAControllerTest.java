package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;

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

public class SLAControllerTest extends CommonTestMVC {


    @Test
    @DisplayName("Просмотр всех отчетов через MVC контроллер, тестирование '/reports'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех SLA через MVC начат");
        mvc.perform(get("/sla")
//                        .param("page", "1")
//                        .param("size", "5")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("sla/viewAllSLA"))
                .andExpect(model().attributeExists("SLA"))
                .andReturn();
    }
    @Test
    @DisplayName("Создание SLA через MVC контроллер, тестирование 'sla/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию типа заявок через MVC начат успешно");
        SLADTO sladto = new SLADTO("MVC_TestSlaName",
                4,
                4,
                new HashSet<>());

        mvc.perform(post("/sla/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("slaForm", sladto)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sla"))
                .andExpect(redirectedUrlTemplate("/sla"))
                .andExpect(redirectedUrl("/sla"));
        log.info("Тест по созданию SLA через MVC закончен успешно");
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


