package ru.sber.spring.java13springmy.sdproject.service;

import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

public class TaskService extends GenericService<Task, TaskDTO> {
    protected TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        super(taskRepository, taskMapper);
    }
}
