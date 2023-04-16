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
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.service.GroupService;

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
public class GroupControllerTest extends CommonTestMVC {
    GroupDTO groupDTO = new GroupDTO("MVC_TestGroupName", new HashSet<>());
    @Autowired
    private GroupService groupService;
    @Test
    @DisplayName("Просмотр всех групп через MVC контроллер, тестирование '/groups'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех ролей через MVC начат");
        mvc.perform(get("/groups")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("groups/viewAllGroup"))
                .andExpect(model().attributeExists("groups"))
                .andReturn();
        log.info("Тест по выбору всех групп завершен успешно");
    }

    @Test
    @DisplayName("Создание группы через MVC контроллер, тестирование 'groups/add'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию группы через MVC начат успешно");
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
        throw new UnsupportedOperationException("Метод обновления категории не поддеживается");
    }
    @Test
    @DisplayName("Soft удаление группы через MVC контроллер, тестирование 'groups/deleteSoft'")
    @Order(2)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void deleteSoft() throws Exception {
        log.info("Тест по soft удалению категории через MVC начат успешно");
        GroupDTO foundGroupForDelete = groupService.findByName(groupDTO.getResponsible());
        mvc.perform(get("/groups/deleteSoft/{id}", foundGroupForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(redirectedUrl("/groups"));
        GroupDTO groupForDelete = groupService.getOne(foundGroupForDelete.getId());
        assertTrue (groupForDelete.isDeleted());
        log.info("Тест soft удаления категории завершен успешно");
    }
    @Test
    @DisplayName("Восстановление группы через MVC контроллер, тестирование 'groups/restore'")
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void restore() throws Exception {
        log.info("Тест по восстановлению группы через MVC начат успешно");
        GroupDTO foundGroupForDelete = groupService.findByName(groupDTO.getResponsible());
        mvc.perform(get("/groups/restore/{id}", foundGroupForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(redirectedUrl("/groups"));
        GroupDTO restoreGroup = groupService.getOne(foundGroupForDelete.getId());
        assertFalse (restoreGroup.isDeleted());
        log.info("Тест восстановления группы завершен успешно");
    }
    @Test
    @DisplayName("Удаление группы через MVC контроллер, тестирование 'groups/delete'")
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void delete() throws Exception {
        log.info("Тест по hard удалению категории через MVC начат успешно");
        GroupDTO foundGroupForDelete = groupService.findByName(groupDTO.getResponsible());
        mvc.perform(get("/groups/delete/{id}", foundGroupForDelete.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/groups"))
                .andExpect(redirectedUrl("/groups"));
        assert (groupService.findByName(groupDTO.getResponsible()) == null);
        log.info("Тест hard удаления категории завершен успешно");
    }
}