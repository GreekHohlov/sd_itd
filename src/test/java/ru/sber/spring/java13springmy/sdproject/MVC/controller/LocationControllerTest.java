package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;

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
public class LocationControllerTest extends CommonTestMVC {
    @Test
    @DisplayName("Просмотр всех площадок через MVC контроллер, тестирование '/locations'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех площадок через MVC начат");
        mvc.perform(get("/locations")
//                        .param("page", "1")
//                        .param("size", "5")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("locations/viewAllLocation"))
                .andExpect(model().attributeExists("locations"))
                .andReturn();
    }

    @Test
    @DisplayName("Создание площадки через MVC контроллер, тестирование 'location/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию площадки через MVC начат успешно");
        LocationDTO locationDTO = new LocationDTO("MVC_TestLocationFormName", new HashSet<>());

        mvc.perform(post("/locations/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("locationForm", locationDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/locations"))
                .andExpect(redirectedUrlTemplate("/locations"))
                .andExpect(redirectedUrl("/locations"));
        log.info("Тест по созданию площадки через MVC закончен успешно");
    }

    @Override
    protected void update() throws Exception {

    }

    @Override
    protected void delete() throws Exception {

    }

    @Test
    @DisplayName("Soft удаление площадки через MVC контроллер, тестирование 'locations/deleteSoft'")
    @Order(4)
//    @Override
    protected void deleteSoft() throws Exception {

    }
}
