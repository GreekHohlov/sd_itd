package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskSearchDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskWithUserMapper;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.utils.FileHelper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
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
    public TaskWithUserDTO getTaskWithUser (Long id) {
        return taskWithUserMapper.toDto(mapper.toEntity(super.getOne(id)));
    }
    public TaskDTO addWorkerToTask(Long taskId, Long workerId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Заявка с id " + taskId + " не найдена"));
        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new NotFoundException("Исполнитель с id " + workerId + " не найден"));
        worker.getTasks().add(task);
        return mapper.toDto(taskRepository.save(task));
    }
    public List<TaskWithUserDTO> getAllTaskWithUser() {
        return taskWithUserMapper.toDTOs(taskRepository.findAll());
    }
    public List<TaskWithUserDTO> findTasks(TaskSearchDTO taskSearchDTO) {
        log.info("Вход: " + taskSearchDTO.toString());
        String taskId = taskSearchDTO.getTaskId() != null ? String.valueOf(taskSearchDTO.getTaskId()) : null;
        String status = taskSearchDTO.getStatusTask() != null ? String.valueOf(taskSearchDTO.getStatusTask().ordinal()) :null;
        String userFio = taskSearchDTO.getUserFio() != null ? ("%" + taskSearchDTO.getUserFio() + "%") : "%";
        log.info("USER_FIO: " + userFio);
        List<TaskWithUserDTO> taskTest = taskWithUserMapper.toDTOs(taskRepository.searchTasks(
                taskId,
                taskSearchDTO.getNameTask(),
//                userFio,
//                taskSearchDTO.getWorkerFio(),
//                taskSearchDTO.getNameCategory(),
                status

        ));
        log.info("Приём: " + taskTest.toString());
        return taskTest;
    }

    //Создание заявкии с файлом
    public TaskDTO create(final TaskDTO object,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file, object.getId());
        object.setFiles(fileName);
        object.setCreatedWhen(LocalDateTime.now());
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

}
