package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskTempDTO;
import ru.sber.spring.java13springmy.sdproject.model.SLA;
import ru.sber.spring.java13springmy.sdproject.service.TypeTaskService;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j

public class TypeTaskControllerTest extends CommonTestMVC {
    private final TypeTaskDTO typeTaskDTO = new TypeTaskDTO("MVC_TestTypeTaskName", 1L, new SLA(), new HashSet<>());
    private final TypeTaskDTO typeTaskDTOUpdate = new TypeTaskDTO("MVC_TestTypeTaskName_Update", 1L, new SLA(), new HashSet<>());
    @Autowired
    private TypeTaskService typeTaskService;

    @Test
    @DisplayName("Просмотр всех типов заявок через MVC контроллер, тестирование '/typetask'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех типов заявок через MVC начат");
        mvc.perform(get("/typetask")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("typetask/viewAllTypeTask"))
                .andExpect(model().attributeExists("typeTask"))
                .andReturn();
        log.info("Тест по выбору всех типов заявок через MVC закончен успешно");
    }
    @Test
    @DisplayName("Создание типа заявки через MVC контроллер, тестирование 'typetask/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию типа заявки через MVC начат успешно");
        mvc.perform(post("/typetask/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("slaForm", typeTaskDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/typetask"))
                .andExpect(redirectedUrlTemplate("/typetask"))
                .andExpect(redirectedUrl("/typetask"));
        log.info("Тест по созданию типа заявки через MVC закончен успешно");
    }
    @Test
    @DisplayName("Обновление типа заявки через MVC контроллер, тестирование 'typetask/update'")
    @Order(2)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void update() throws Exception {
        log.info("Тест по обновлению типа заявки через MVC начат успешно");
        TypeTaskTempDTO foundTypeTask = typeTaskService.findByName(typeTaskDTO.getNameType());
        mvc.perform(post("/typetask/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("typeId", foundTypeTask.getId())
                        .flashAttr("nameType", typeTaskDTOUpdate.getNameType())
                        .flashAttr("slaId", typeTaskDTOUpdate.getSlaId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/typetask"))
                .andExpect(redirectedUrl("/typetask"));
        log.info("Тест по обновлению типа заявки через MVC закончен успешно");
    }
    @Test
    @DisplayName("Soft удаление типа заявки через MVC контроллер, тестирование 'typetask/deleteSoft'")
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void deleteSoft() throws Exception {
        log.info("Тест по обновлению типа заявки через MVC начат успешно");
        TypeTaskTempDTO foundTypeTask = typeTaskService.findByName(typeTaskDTO.getNameType());
        mvc.perform(get("/typetask/deleteSoft/{id}", foundTypeTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/typetask"))
                .andExpect(redirectedUrl("/typetask"));
        TypeTaskTempDTO typeTaskForDelete = typeTaskService.findByName(typeTaskDTO.getNameType());
        assertTrue (typeTaskForDelete.isDeleted());
        log.info("Тест soft удаления типа заявки завершен успешно");
    }
    @Test
    @DisplayName("Восстановление типа заявки через MVC контроллер, тестирование 'typetask/restore'")
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void restore() throws Exception {
        log.info("Тест по восстановлению типа заявки через MVC начат успешно");
        TypeTaskTempDTO foundTypeTask = typeTaskService.findByName(typeTaskDTO.getNameType());
        mvc.perform(get("/typetask/restore/{id}", foundTypeTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/typetask"))
                .andExpect(redirectedUrl("/typetask"));
        TypeTaskTempDTO typeTaskForRestore = typeTaskService.findByName(typeTaskDTO.getNameType());
        assertFalse (typeTaskForRestore.isDeleted());
        log.info("Тест восстановления типа заявки завершен успешно");
    }
    @Test
    @DisplayName("Удаление типа заявки через MVC контроллер, тестирование 'typetask/delete'")
    @Order(5)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void delete() throws Exception {
        log.info("Тест по Hard удалению типа заявки через MVC начат успешно");
        TypeTaskTempDTO foundTypeTask = typeTaskService.findByName(typeTaskDTO.getNameType());
        mvc.perform(get("/typetask/delete/{id}", foundTypeTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/typetask"))
                .andExpect(redirectedUrl("/typetask"));
        assert (typeTaskService.findByName(typeTaskDTO.getNameType()) == null);
        log.info("Тест hard удаления типа заявки завершен успешно");
    }

}


