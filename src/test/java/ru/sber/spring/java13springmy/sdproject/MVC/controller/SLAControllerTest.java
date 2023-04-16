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
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.service.SLAService;

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

public class SLAControllerTest extends CommonTestMVC {
    private final SLADTO sladto = new SLADTO("MVC_TestSlaName",4,4, new HashSet<>());
    @Autowired
    private SLAService slaService;

    @Test
    @DisplayName("Просмотр всех SLA через MVC контроллер, тестирование '/sla'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех SLA через MVC начат");
        mvc.perform(get("/sla")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("sla/viewAllSLA"))
                .andExpect(model().attributeExists("SLA"))
                .andReturn();
        log.info("Тест по выбору всех SLA через MVC закончен успешно");
    }
    @Test
    @DisplayName("Создание SLA через MVC контроллер, тестирование 'sla/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию SLA через MVC начат успешно");
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
        throw new UnsupportedOperationException("Метод обновления SLA не поддеживается");
    }
    @Test
    @DisplayName("Soft удаление SLA через MVC контроллер, тестирование 'sla/deleteSoft'")
    @Order(2)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void deleteSoft() throws Exception {
        log.info("Тест по soft удалению sla через MVC начат успешно");
        SLADTO foundSlaForDelete = slaService.findByName(sladto.getNameSLA());
        mvc.perform(get("/sla/deleteSoft/{id}", foundSlaForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sla"))
                .andExpect(redirectedUrl("/sla"));
        SLADTO slaForDelete = slaService.getOne(foundSlaForDelete.getId());
        assertTrue (slaForDelete.isDeleted());
        log.info("Тест soft удаления sla завершен успешно");
    }
    @Test
    @DisplayName("Восстановление sla через MVC контроллер, тестирование 'sla/restore'")
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void restore() throws Exception {
        log.info("Тест по восстановлению sla через MVC начат успешно");
        SLADTO foundSlaForDelete = slaService.findByName(sladto.getNameSLA());
        mvc.perform(get("/sla/restore/{id}", foundSlaForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sla"))
                .andExpect(redirectedUrl("/sla"));
        SLADTO slaForRestore = slaService.getOne(foundSlaForDelete.getId());
        assertFalse (slaForRestore.isDeleted());
        log.info("Тест восстановления sla завершен успешно");
    }
    @Test
    @DisplayName("Удаление sla через MVC контроллер, тестирование 'sla/delete'")
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void delete() throws Exception {
        log.info("Тест по Hard удалению sla через MVC начат успешно");
        SLADTO foundSlaForDelete = slaService.findByName(sladto.getNameSLA());
        mvc.perform(get("/sla/delete/{id}", foundSlaForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/sla"))
                .andExpect(redirectedUrl("/sla"));
        assert (slaService.findByName(sladto.getNameSLA()) == null);
        log.info("Тест hard удаления sla завершен успешно");
    }

}


