package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.service.LocationService;

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
public class LocationControllerTest extends CommonTestMVC {
    private final LocationDTO locationDTO = new LocationDTO("MVC_TestLocationFormName", new HashSet<>());
    @Autowired
    private LocationService locationService;
    @Test
    @DisplayName("Просмотр всех площадок через MVC контроллер, тестирование '/groups'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех площадок через MVC начат");
        mvc.perform(get("/locations")
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
        throw new UnsupportedOperationException("Метод обновления категории не поддеживается");
    }



    @Test
    @DisplayName("Soft удаление площадки через MVC контроллер, тестирование 'locations/deleteSoft'")
    @Order(2)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void deleteSoft() throws Exception {
        log.info("Тест по soft удалению площадки через MVC начат успешно");
        LocationDTO foundLocationForDelete = locationService.findByName(locationDTO.getNameLocation());
        mvc.perform(get("/locations/deleteSoft/{id}", foundLocationForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/locations"))
                .andExpect(redirectedUrl("/locations"));
        LocationDTO locationForDelete = locationService.getOne(foundLocationForDelete.getId());
        assertTrue (locationForDelete.isDeleted());
        log.info("Тест soft удаления площадки завершен успешно");
    }
    @Test
    @DisplayName("Восстановление площадки через MVC контроллер, тестирование 'locations/restore'")
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void restore() throws Exception {
        log.info("Тест по восстановлению площадки через MVC начат успешно");
        LocationDTO foundLocationForDelete = locationService.findByName(locationDTO.getNameLocation());
        mvc.perform(get("/locations/restore/{id}", foundLocationForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/locations"))
                .andExpect(redirectedUrl("/locations"));
        LocationDTO restoreLocation = locationService.getOne(foundLocationForDelete.getId());
        assertFalse (restoreLocation.isDeleted());
        log.info("Тест восстановления площадки завершен успешно");
    }
    @Test
    @DisplayName("Удаление площадки через MVC контроллер, тестирование 'locations/delete'")
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void delete() throws Exception {
        log.info("Тест по Hard удалению площадки через MVC начат успешно");
        LocationDTO foundLocationForDelete = locationService.findByName(locationDTO.getNameLocation());
        mvc.perform(get("/locations/delete/{id}", foundLocationForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/locations"))
                .andExpect(redirectedUrl("/locations"));
        assert (locationService.findByName(locationDTO.getNameLocation()) == null);
        log.info("Тест hard удаления категории завершен успешно");
    }
}