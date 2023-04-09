package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetailsService;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
public class GroupControllerTest extends CommonTest {

    private static Long createdGroupID;

    @Test
    @Order(0)
    protected void listAll() throws Exception {
        log.info("Тест по просмотра всех Групп через MVC начат успешно");
        String result = mvc.perform(get("/groups")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        //  List<GroupDTO> groupDTOS = objectMapper.readValue(result, new TypeReference<>() {});
        log.info(result);
        log.info("Тест по просмотра всех Групп через MVC закончен успешно");
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    protected void createObject() throws Exception {
//        log.info("Тест по созданию автора через REST начат успешно");
//        //Создаем нового автора для создания через контроллер (тест дата)
//        GroupDTO groupDTO = new GroupDTO("TestName", new HashSet<>());
//
//        String result = mvc.perform(post("/groups/add")
//                .with((RequestPostProcessor) userDetailsService))
//                      //  .headers(headers)
////                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//////                        .headers(super.headers)
////                        .content(asJsonString(groupDTO))
////                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                //      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        // Long createdGroupID = result.getId();
//        log.info(result);
//        log.info("Тест по созданию автора через REST закончен успешно " + result);

    }



    @Test
    @WithMockUser(username = "admin", password = "123", roles = "ADMIN")
    @Order(3)
    @Override
    protected void updateObject() throws Exception {
//        log.info("Тест по обновления автора через REST начат успешно");
//        //получаем нашего автора созданного (если запускать тесты подряд), если отдельно - создаем отдельную тест дату для апдейта
//        GroupDTO existingAuthor = objectMapper.readValue(mvc.perform(get("/groups/getOneById")
//                                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                                .headers(headers)
//                                .param("id", String.valueOf(5L))
//                                .accept(MediaType.APPLICATION_JSON_VALUE))
//                        .andExpect(status().isOk())
//                     //   .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                        .andReturn()
//                        .getResponse()
//                        .getContentAsString(),
//                GroupDTO.class);
//        log.info("Тест по удалению автора через REST завершен успешно");
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = "ADMIN")
    @Order(4)
    protected void deleteObject() throws Exception {
//        log.info("Тест по удалению автора через REST начат успешно");
//        mvc.perform(delete("/groups/delete/{id}", createdGroupID)
//                        .headers(headers)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk());
//        GroupDTO existingGroup = objectMapper.readValue(mvc.perform(get("/groups/getOneById")
//                                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                                .headers(headers)
//                                .param("id", String.valueOf(createdGroupID))
//                                .accept(MediaType.APPLICATION_JSON_VALUE))
//                        .andExpect(status().isOk())
//                        //  .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                        .andReturn()
//                        .getResponse()
//                        .getContentAsString(),
//                GroupDTO.class);
//
//        assertTrue(existingGroup.isDeleted());
//        log.info("Тест по удалению автора через REST завершен успешно");
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
