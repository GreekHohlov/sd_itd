package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.TaskTestData;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskWithUserMapper;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest extends GenericTest<Task, TaskDTO> {

    public TaskServiceTest() {
        super();
        repository = Mockito.mock(TaskRepository.class);
        mapper = Mockito.mock(TaskMapper.class);
        TaskWithUserMapper taskWithUserMapper = Mockito.mock(TaskWithUserMapper.class);
        SLAService slaService = Mockito.mock(SLAService.class);
        TypeTaskRepository typeTaskRepository = Mockito.mock(TypeTaskRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        HistoryService historyService = Mockito.mock(HistoryService.class);
        service = new TaskService((TaskRepository) repository, (TaskMapper) mapper, taskWithUserMapper,
                slaService, typeTaskRepository, userRepository, historyService);
    }

    @Test
    @Order(1)
    @Override
    protected void listAll() {
        Mockito.when(repository.findAll()).thenReturn(TaskTestData.TASK_LIST);
        Mockito.when(mapper.toDTOs(TaskTestData.TASK_LIST)).thenReturn(TaskTestData.TASK_DTO_LIST);
        List<TaskDTO> taskDTOS = service.listAll();
        log.info("Testing getAll(): " + taskDTOS);
        assertEquals(TaskTestData.TASK_LIST.size(), taskDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TaskTestData.TASK_1));
        Mockito.when(mapper.toDto(TaskTestData.TASK_1)).thenReturn(TaskTestData.TASK_DTO_1);
        TaskDTO taskDTO = service.getOne(1L);
        log.info("Testing getOne(): " + taskDTO);
        assertEquals(TaskTestData.TASK_DTO_1, taskDTO);
    }

    @Test
    @Order(3)
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(TaskTestData.TASK_DTO_1)).thenReturn(TaskTestData.TASK_1);
        Mockito.when(mapper.toDto(TaskTestData.TASK_1)).thenReturn(TaskTestData.TASK_DTO_1);
        Mockito.when(repository.save(TaskTestData.TASK_1)).thenReturn(TaskTestData.TASK_1);
        TaskDTO taskDTO = service.create(TaskTestData.TASK_DTO_1);
        log.info("Testing create(): " + taskDTO);
        assertEquals(TaskTestData.TASK_DTO_1, taskDTO);
    }

    @Test
    @Order(4)
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(TaskTestData.TASK_DTO_2)).thenReturn(TaskTestData.TASK_2);
        Mockito.when(mapper.toDto(TaskTestData.TASK_2)).thenReturn(TaskTestData.TASK_DTO_2);
        Mockito.when(repository.save(TaskTestData.TASK_2)).thenReturn(TaskTestData.TASK_2);
        TaskDTO taskDTO = service.update(TaskTestData.TASK_DTO_2);
        log.info("Testing create(): " + taskDTO);
        assertEquals(TaskTestData.TASK_DTO_2, taskDTO);
    }

    @Test
    @Order(5)
    @Override
    protected void delete() throws MyDeleteException {

    }

    @Test
    @Order(6)
    protected void restore() {
        TaskTestData.TASK_2.setDeleted(true);
        Mockito.when(repository.save(TaskTestData.TASK_2)).thenReturn(TaskTestData.TASK_2);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(TaskTestData.TASK_2));
        log.info("Testing restore() before: " + TaskTestData.TASK_2.isDeleted());
        ((TaskService) service).restore(2L);
        log.info("Testing restore() after: " + TaskTestData.TASK_2.isDeleted());
        assertFalse(TaskTestData.TASK_2.isDeleted());
    }
}
