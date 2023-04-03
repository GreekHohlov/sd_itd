package ru.sber.spring.java13springmy.sdproject.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskWithUserMapper;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

public class TaskServiceTest extends GenericTest<Task, TaskDTO> {

    public TaskServiceTest() {
        super();
        TaskWithUserMapper taskWithUserMapper = Mockito.mock(TaskWithUserMapper.class);
        TaskMapper taskMapper = Mockito.mock(TaskMapper.class);
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        service = new TaskService(taskRepository, taskMapper, taskWithUserMapper);
    }

    @Test
    @Override
    protected void getAll() {

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
