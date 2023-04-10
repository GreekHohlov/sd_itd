package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BindingResult;
import ru.sber.spring.java13springmy.GroupTestData;
import ru.sber.spring.java13springmy.LocationTestData;
import ru.sber.spring.java13springmy.RoleTestData;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;

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
public class UserControllerTest extends CommonTestMVC {
    @Test
    @DisplayName("Просмотр всех пользователей через MVC контроллер, тестирование '/locations'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех площадок через MVC начат");
        mvc.perform(get("/users")
                        .param("page", "1")
                        .param("size", "5")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/viewAllUsers"))
                .andExpect(model().attributeExists("users"))
                .andReturn();
    }

    @Test
    @DisplayName("Создание пользователя через MVC контроллер, тестирование 'users/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию пользователя через MVC начат успешно");
        UserDTO userDTO = new UserDTO("MVC_TestUserFirstName",
                "MVC_TestUserMiddleName",
                "MVC_TestUserLastName",
                "login","password",
                "mail.ru",
                "+79999555",
                1,
                1,
                RoleTestData.ROLE_DTO1,
                null,
                false,
                new HashSet<>(),
                new HashSet<>(),
                GroupTestData.GROUP_1,
                LocationTestData.LOCATION_1,
                false);

        mvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"))
                .andExpect(redirectedUrlTemplate("/users"))
                .andExpect(redirectedUrl("/users"));
        log.info("Тест по созданию пользователя через MVC закончен успешно");
    }

    @Test
    @DisplayName("Регистрация пользователя через MVC контроллер, тестирование 'registration'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void registration() throws Exception {
        log.info("Тест по регистрации пользователя через MVC начат успешно");
        UserDTO userDTO = new UserDTO("MVC_TestUserFirstName",
                "MVC_TestUserMiddleName",
                "MVC_TestUserLastName",
                "login","password",
                "mail.ru",
                "+79999555",
                1,
                1,
                RoleTestData.ROLE_DTO1,
                null,
                false,
                new HashSet<>(),
                new HashSet<>(),
                GroupTestData.GROUP_1,
                LocationTestData.LOCATION_1,
                false);

        mvc.perform(post("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("registration"));
        log.info("Тест по созданию пользователя через MVC закончен успешно");

    }

    @Override
    protected void update() throws Exception {

    }

    @Override
    protected void delete() throws Exception {

    }

    @Test
    @DisplayName("Soft удаление пользователя через MVC контроллер, тестирование 'locations/deleteSoft'")
    @Order(4)
//    @Override
    protected void deleteSoft() throws Exception {

    }
}
