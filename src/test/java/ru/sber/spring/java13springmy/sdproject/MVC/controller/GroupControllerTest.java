package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;

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

public class GroupControllerTest extends CommonTestMVC {
    @Test
    @DisplayName("Просмотр всех авторов через MVC контроллер, тестирование '/groups'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех ролей через MVC начат");
        mvc.perform(get("/groups")
//                        .param("page", "1")
//                        .param("size", "5")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("groups/viewAllGroup"))
                .andExpect(model().attributeExists("groups"))
                .andReturn();
    }

    @Test
    @DisplayName("Создание группы через MVC контроллер, тестирование 'groups/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию группы через MVC начат успешно");
        GroupDTO groupDTO = new GroupDTO("MVC_TestGroupName", new HashSet<>());

        mvc.perform(post("/groups/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("groupForm", groupDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(redirectedUrlTemplate("/groups"))
                .andExpect(redirectedUrl("/groups"));
        log.info("Тест по созданию группы через MVC закончен успешно");
    }

    @Override
    protected void update() throws Exception {

    }

    @Test
    @DisplayName("Soft удаление группы через MVC контроллер, тестирование 'groups/deleteSoft'")
    @Order(4)
    @Override
    protected void deleteSoft() throws Exception {

    }
}


