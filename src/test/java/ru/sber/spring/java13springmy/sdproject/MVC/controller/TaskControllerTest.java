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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import ru.sber.spring.java13springmy.sdproject.dto.HistoryDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskSearchDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.model.Priority;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;
import ru.sber.spring.java13springmy.sdproject.service.HistoryService;
import ru.sber.spring.java13springmy.sdproject.service.TaskService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@Rollback(value = false)
@Slf4j

public class TaskControllerTest extends CommonTestMVC {
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    private final TaskDTO taskDTO = new TaskDTO("MVC_TestTaskName", 1L, Priority.HIGH, 1L,
            "description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1L), new HashSet<Long>(),
            1L, 1L, StatusTask.OPEN, null, "decision1", new HashSet<Long>());
    private final TaskDTO taskDTOUpdated = new TaskDTO("MVC_TestTaskName_Updated", 1L, Priority.HIGH, 1L,
            "description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1L), new HashSet<Long>(),
            1L, 1L, StatusTask.OPEN, null, "decision1", new HashSet<Long>());

    private final MockMultipartFile file = new MockMultipartFile("file", "test contract.pdf", MediaType.APPLICATION_PDF_VALUE,
            "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
    @Order(0)
    @Test
    @DisplayName("Просмотр всех заявок через MVC контроллер, тестирование '/task'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void getAll() throws Exception {
        log.info("Тест по выбору всех ролей через MVC начат");
        mvc.perform(get("/task")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("task/viewAllTask"))
                .andExpect(model().attributeExists("task"))
                .andReturn();
    }
    @Order(1)
    @Test
    @DisplayName("Создание заявки через MVC контроллер, тестирование 'task/add'")
    @WithMockUser(username = "A_service", roles = "EXECUTOR", password = "D1plom$54")
    @Override
    protected void create() throws Exception {
        log.info("Тест по созданию группы через MVC начат успешно");

        mvc.perform(multipart("/task/add")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("taskForm", taskDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrlTemplate("/task"))
                .andExpect(redirectedUrl("/task"));

        log.info("Тест по созданию заявки через MVC закончен успешно");
    }
    @Order(2)
    @Test
    @DisplayName("Обновление заявки через MVC контроллер, тестирование 'task/update'")
    @WithMockUser(username = "A_service", roles = "EXECUTOR", password = "D1plom$54")
    @Override
    protected void update() throws Exception {
        log.info("Тест по обновлению заявки через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        foundTask.setNameTask(taskDTOUpdated.getNameTask());
        mvc.perform(multipart("/task/update")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("taskForm", foundTask)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"));
        log.info("Тест по обновлению заявки через MVC закончен успешно");

    }
    @Order(3)
    @Test
    @DisplayName("Удаление заявки через MVC контроллер, тестирование 'task/deleteSoft'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void delete() throws Exception {
        log.info("Тест по удалению заявки через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        mvc.perform(get("/task/delete/{id}", foundTask.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"));
        TaskDTO deletedTask = taskService.getOne(foundTask.getId());
        assertTrue(deletedTask.isDeleted());
        log.info("Данные очищены");
    }
    @Order(4)
    @Test
    @DisplayName("Восстановление заявки через MVC контроллер, тестирование 'task/restore'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void restore() throws Exception {
        log.info("Тест по восстановлению заявки через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        mvc.perform(get("/task/restore/{id}", foundTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"));
        TaskDTO restoredTask = taskService.getOne(foundTask.getId());
        assertFalse(restoredTask.isDeleted());
        log.info("Данные очищены");
    }
    @Order(5)
    @Test
    @DisplayName("Взять заявку через MVC контроллер, тестирование 'task/takeTask'")
    @WithMockUser(username = "Ex_greek", roles = "EXECUTOR", password = "greek")
    protected void takeTask() throws Exception {
        log.info("Тест по восстановлению заявки через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        mvc.perform(get("/task/takeTask/{id}", foundTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"));
        TaskDTO takeTask = taskService.getOne(foundTask.getId());
        assert (takeTask.getWorkerId() == 3L);
        log.info("Данные очищены");
    }
    @Order(6)
    @Test
    @DisplayName("Открыть историю заявки через MVC контроллер, тестирование 'task/history'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void history() throws Exception {
        log.info("Тест по просмотру истории заявки через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        List<HistoryDTO> historyDTO = historyService.findAllByTaskId(foundTask.getId());
        mvc.perform(get("/task/history/{id}", foundTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("historyForm", historyDTO)
                        .flashAttr("task", taskService.getOne(foundTask.getId()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("task/viewTaskHistory"))
                .andExpect(model().attributeExists("task"))
                .andReturn();
        log.info("История заявки открыта");
    }
    @Order(7)
    @Test
    @DisplayName("Остановить заявку через MVC контроллер, тестирование 'task/stopTask'")
    @WithMockUser(username = "Ex_greek", roles = "EXECUTOR", password = "greek")
    protected void stopTask() throws Exception {
        log.info("Тест по остановке заявки через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        TaskDTO taskForStop = taskService.getOne(foundTask.getId());
        taskForStop.setDecision("Остановить заявку через MVC контроллер");
        mvc.perform(post("/task/stopTask")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("taskStopForm", taskForStop)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"))
                .andReturn();
        TaskDTO stoppedTask = taskService.getOne(foundTask.getId());
        assert (stoppedTask.getStatusTask().ordinal() == 2L);
        log.info("Заявка остановлена");
    }
    @Order(8)
    @Test
    @DisplayName("Вернуть заявку в работу через MVC контроллер, тестирование 'task/unstopTask'")
    @WithMockUser(username = "Ex_greek", roles = "EXECUTOR", password = "greek")
    protected void unstopTask() throws Exception {
        log.info("Тест по возврату заявки в работу через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        mvc.perform(get("/task/unstopTask/{id}", foundTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"));
        TaskDTO unstopTask = taskService.getOne(foundTask.getId());
        assert (unstopTask.getStatusTask().ordinal() == 1L);
        log.info("Данные очищены");
    }
    @Order(9)
    @Test
    @DisplayName("Выполнить заявку через MVC контроллер, тестирование 'task/executeTask'")
    @WithMockUser(username = "Ex_greek", roles = "EXECUTOR", password = "greek")
    protected void executeTask() throws Exception {
        log.info("Тест по выполнению заявки через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        TaskDTO taskForExecute = taskService.getOne(foundTask.getId());
        taskForExecute.setDecision("Выполнить заявку через MVC контроллер");
        mvc.perform(post("/task/executeTask")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("taskExecForm", taskForExecute)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"))
                .andReturn();
        TaskDTO stoppedTask = taskService.getOne(foundTask.getId());
        assert (stoppedTask.getStatusTask().ordinal() == 3L);
        log.info("Заявка выполнена");
    }
    @Order(10)
    @Test
    @DisplayName("Отменить выполнение заявки через MVC контроллер, тестирование 'task/noexecuteTask'")
    @WithMockUser(username = "A_service", roles = "EXECUTOR", password = "D1plom$54")
    protected void noexecuteTask() throws Exception {
        log.info("Тест по отмене выполнения заявки в работу через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        mvc.perform(get("/task/noexecuteTask/{id}", foundTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"));
        TaskDTO noexecuteTask = taskService.getOne(foundTask.getId());
        assert (noexecuteTask.getStatusTask().ordinal() == 1L);
        log.info("Данные очищены");
    }
    @Order(11)
    @Test
    @DisplayName("Отменить выполнение заявки через MVC контроллер, тестирование 'task/closeTask'")
    @WithMockUser(username = "A_service", roles = "EXECUTOR", password = "D1plom$54")
    protected void closeTask() throws Exception {
        log.info("Тест по отмене выполнения заявки в работу через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setNameTask(taskDTO.getNameTask());
        TaskWithUserDTO foundTask = taskService.findTasks(taskSearchDTO, pageRequest).getContent().get(0);
        mvc.perform(get("/task/closeTask/{id}", foundTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task"))
                .andExpect(redirectedUrl("/task"));
        TaskDTO executeTask = taskService.getOne(foundTask.getId());
        assert (executeTask.getStatusTask().ordinal() == 4L);
        log.info("Данные очищены");
    }
}