package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import ru.sber.spring.java13springmy.GroupTestData;
import ru.sber.spring.java13springmy.LocationTestData;
import ru.sber.spring.java13springmy.RoleTestData;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.service.UserService;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@Rollback(value = false)
@Slf4j
public class UserControllerTest extends CommonTestMVC {
    private final UserDTO userDTOCreate = new UserDTO("MVC_TestUserFirstName",
            "MVC_TestUserMiddleName", "MVC_TestUserLastName", "login", "password",
            "MVC_TestUser@mail.ru", "+79999555", 1, 1, RoleTestData.ROLE_DTO1,
            null, false, new HashSet<>(), new HashSet<>(), GroupTestData.GROUP_1,
            LocationTestData.LOCATION_1, false);
    private final UserDTO userDTORegistered = new UserDTO("MVC_TestUserRegistrationFirstName",
            "MVC_TestUserRegistrationMiddleName", "MVC_TestUserRegistrationLastName",
            "RegistrationLogin", "password", "MVC_TestUserRegistration@mail.ru",
            "+79999555", 1, 1, RoleTestData.ROLE_DTO1, null,
            false, new HashSet<>(), new HashSet<>(), GroupTestData.GROUP_1, LocationTestData.LOCATION_1,
            false);
    private final UserDTO userDTOUpdated = new UserDTO("MVC_TestUserUpdatedFirstName",
            "MVC_TestUserUpdatedMiddleName", "MVC_TestUserUpdatedLastName",
            "RegistrationLogin", "password", "MVC_TestUserRegistration@mail.ru",
            "+79999555", 1, 1, RoleTestData.ROLE_DTO1, null,
            false, new HashSet<>(), new HashSet<>(), GroupTestData.GROUP_1, LocationTestData.LOCATION_1,
            false);
    private final UserDTO userDTOExecutor = new UserDTO("MVC_TestUserExecutorFirstName",
            "MVC_TestUserExecutorMiddleName", "MVC_TestUserExecutorLastName",
            "ExecutorLogin", "password", "MVC_TestUserExecutor@mail.ru", "+79999555",
            1, 1, RoleTestData.ROLE_DTO1, null, false, new HashSet<>(),
            new HashSet<>(), GroupTestData.GROUP_1, LocationTestData.LOCATION_1, false);
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Просмотр всех пользователей через MVC контроллер, тестирование '/locations'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по просмотру всех пользователей через MVC начат");
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
        log.info("Тест по просмотру всех пользователей через MVC закончен успешно");
    }

    @Test
    @DisplayName("Создание пользователя через MVC контроллер, тестирование 'users/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию пользователя через MVC начат успешно");
        mvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDTOCreate)
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
    @DisplayName("Регистрация пользователя через MVC контроллер, тестирование 'users/registration'")
    @Order(2)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void registration() throws Exception {
        log.info("Тест по регистрации пользователя через MVC начат успешно");
        mvc.perform(post("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDTORegistered)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"))
                .andExpect(redirectedUrl("login"));
        assert (userService.getUserByEmail(userDTORegistered.getEmail()) != null);
        log.info("Тест по регистрации пользователя через MVC закончен успешно");
    }

    @Test
    @DisplayName("Создание пользователя с ролью EXECUTOR через MVC контроллер, тестирование 'users/add-executor'")
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void addLibrarian() throws Exception {
        log.info("Тест по регистрации пользователя с ролью EXECUTOR через MVC начат успешно");
        mvc.perform(post("/users/add-executor")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDTOExecutor)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"))
                .andExpect(redirectedUrl("/users/list"));
        assert (userService.getUserByEmail(userDTORegistered.getEmail()) != null);
        log.info("Тест по регистрации пользователя с ролью EXECUTOR через MVC закончен успешно");
    }

    @Test
    @DisplayName("Просмотр профиля пользователя через MVC контроллер, тестирование 'users/delete'")
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void userProfile() throws Exception {
        log.info("Тест по просмотру профиля пользователя через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "first_name"));
        UserDTO foundUserForView = userService.findUsers(userDTOCreate, pageRequest).getContent().get(0);
        mvc.perform(get("/users/profile/{id}", foundUserForView.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("user", foundUserForView)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profile/viewProfile"))
                .andExpect(model().attributeExists("user"));
        log.info("Тест по просмотру профиля пользователя через MVC закончен успешно");
    }

    @Test
    @DisplayName("Обновлению пользователя через MVC контроллер, тестирование 'users/profile/update'")
    @Order(5)
    @WithMockUser(username = "RegistrationLogin", roles = "USER", password = "password")
    @Override
    protected void update() throws Exception {
        log.info("Тест по обновлению пользователя через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "first_name"));
        UserDTO foundUserForUpdate = userService.findUsers(userDTORegistered, pageRequest).getContent().get(0);
        foundUserForUpdate.setLastName(userDTOUpdated.getLastName());
        foundUserForUpdate.setFirstName(userDTOUpdated.getFirstName());
        foundUserForUpdate.setMiddleName(userDTOUpdated.getMiddleName());
        mvc.perform(post("/users/profile/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", foundUserForUpdate)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/profile/" + foundUserForUpdate.getId()))
                .andExpect(redirectedUrl("/users/profile/" + foundUserForUpdate.getId()));
        log.info("Тест по обновлению пользователя через MVC закончен успешно");
    }

    @Test
    @DisplayName("Soft удаление пользователя через MVC контроллер, тестирование 'users/delete'")
    @Order(6)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void delete() throws Exception {
        log.info("Тест по удалению пользователя через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "first_name"));
        UserDTO foundUserForDelete = userService.findUsers(userDTOCreate, pageRequest).getContent().get(0);
        mvc.perform(get("/users/delete/{id}", foundUserForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"))
                .andExpect(redirectedUrl("/users/list"));
        UserDTO deletedUser = userService.getOne(foundUserForDelete.getId());
        assertTrue(deletedUser.isDeleted());
        log.info("Тест по удалению пользователя через MVC закончен успешно");
    }

    @Test
    @DisplayName("Восстановлению пользователя через MVC контроллер, тестирование 'users/restore'")
    @Order(7)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void restore() throws Exception {
        log.info("Тест по восстановлению пользователя через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "first_name"));
        UserDTO foundUserForDelete = userService.findUsers(userDTOCreate, pageRequest).getContent().get(0);
        mvc.perform(get("/users/restore/{id}", foundUserForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"))
                .andExpect(redirectedUrl("/users/list"));
        UserDTO restoredUser = userService.getOne(foundUserForDelete.getId());
        assertFalse (restoredUser.isDeleted());
        log.info("Тест по восстановлению пользователя через MVC закончен успешно");
    }
}
