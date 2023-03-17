package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskWithUserMapper;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.List;

@Service
public class TaskService extends GenericService<Task, TaskDTO> {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskWithUserMapper taskWithUserMapper;
    protected TaskService(TaskRepository taskRepository, TaskMapper taskMapper,
                          UserRepository userRepository, TaskWithUserMapper taskWithUserMapper) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskWithUserMapper = taskWithUserMapper;
    }

    public TaskDTO addUserToTask(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Заявка с id " + taskId + " не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        user.getTasks().add(task);
        return mapper.toDto(taskRepository.save(task));
    }
    public List<TaskWithUserDTO> getAllTaskWithUser() {
        return taskWithUserMapper.toDTOs(taskRepository.findAll());
    }
}
