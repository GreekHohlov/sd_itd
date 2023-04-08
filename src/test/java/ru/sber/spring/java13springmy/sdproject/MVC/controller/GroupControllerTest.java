package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.spring.java13springmy.sdproject.MVC.controller.CommonTest;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j

public class GroupControllerTest extends CommonTest {


    @Test
    @WithMockUser(username = "admin", password = "123", roles = "ADMIN")
    @Order(0)
    protected void listAll() throws Exception {
        log.info("Тест по просмотра всех Групп через MVC начат успешно");
        String result = mvc.perform(get ("/groups")
//                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.*", hasSize(greaterThan(0))))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // List<GroupDTO> groupDTOS = objectMapper.readValue(result, new TypeReference<>() {});
//        groupDTOS .forEach(a -> log.info(a.toString()));
        log.info("Тест по просмотра всех Групп через MVC закончен успешно");
    }

    @Test
    @WithMockUser(username = "admin", password = "123", roles = "ADMIN",  setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Order(1)
    protected void createObject() throws Exception {
        log.info("Тест по созданию автора через REST начат успешно");
        //Создаем нового автора для создания через контроллер (тест дата)
        GroupDTO groupDTO= new GroupDTO("TestAuthorFio",new HashSet<>());
        GroupDTO result = objectMapper.readValue(mvc.perform(post("/groups/add")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .headers(headers)
                                .content(asJsonString(groupDTO))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        //      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                GroupDTO.class);
        // Long createdGroupID = result.getId();
        log.info("Тест по созданию автора через REST закончен успешно " + result);

    }
    @Test
    @WithMockUser(username = "admin", password = "123", roles = "ADMIN")
    @Order(1)
    @Override
    protected void updateObject() throws Exception {

    }

    @Override
    protected void deleteObject() throws Exception {

    }
}
