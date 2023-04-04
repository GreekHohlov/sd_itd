package ru.sber.spring.java13springmy.sdproject.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskWithUserMapper;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest extends GenericTest<Task, TaskDTO> {

    public TaskServiceTest() {
        super();
        TaskWithUserMapper taskWithUserMapper = Mockito.mock(TaskWithUserMapper.class);
        TaskMapper taskMapper = Mockito.mock(TaskMapper.class);
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        service = new TaskService(taskRepository, taskMapper, taskWithUserMapper);
    }

    @Test
    @Order(1)
    @Override
    protected void getAll() {
      //  Mockito.when(repository.findAll()).thenReturn(TaskTestData.TASK_LIST);
//        Mockito.when(mapper.toDTOs(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);
//        List<AuthorDTO> authorDTOS = service.listAll();
//        log.info("Testing getAll(): " + authorDTOS);
//        assertEquals(AuthorTestData.AUTHOR_LIST.size(), authorDTOS.size());

    }

    @Override
    protected void getOne() {

    }

    @Override
    protected void create() {

    }

    @Override
    protected void update() {

    }

    @Override
    protected void delete() throws MyDeleteException {

    }

    @Override
    protected void restore() {

    }

    @Override
    protected void getAllNotDeleted() {

    }
}
