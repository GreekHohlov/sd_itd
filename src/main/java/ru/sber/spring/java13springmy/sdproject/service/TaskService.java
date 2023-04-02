package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskFotWorkingDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskSearchDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskFotWorkingMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskWithUserMapper;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetails;
import ru.sber.spring.java13springmy.sdproject.utils.FileHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TaskService extends GenericService<Task, TaskDTO> {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskWithUserMapper taskWithUserMapper;
    private final TaskFotWorkingMapper taskFotWorkingMapper;

    protected TaskService(TaskRepository taskRepository,
                          TaskMapper taskMapper,
                          UserRepository userRepository,
                          TaskWithUserMapper taskWithUserMapper,
                          TaskFotWorkingMapper taskFotWorkingMapper) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskWithUserMapper = taskWithUserMapper;
        this.taskFotWorkingMapper = taskFotWorkingMapper;
    }

    public Page<TaskWithUserDTO> getAllTasksWithUsers(Pageable pageable) {
        Page<Task> tasksPaginated = taskRepository.findAll(pageable);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasksPaginated.getContent());
        return new PageImpl<>(result, pageable, tasksPaginated.getTotalElements());
    }

    public Page<TaskWithUserDTO> getAllNotDeletedTasksWithUsers(Pageable pageable) {
        Page<Task> tasksPaginated = taskRepository.findAllByIsDeletedFalse(pageable);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasksPaginated.getContent());
        return new PageImpl<>(result, pageable, tasksPaginated.getTotalElements());
    }

    public TaskDTO addUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Заявка с id " + taskId + " не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));
        user.getTasks().add(task);
        return mapper.toDto(taskRepository.save(task));
    }

    public TaskWithUserDTO getTaskWithUser(Long id) {
        return taskWithUserMapper.toDto(mapper.toEntity(super.getOne(id)));
    }

    public TaskDTO addWorkerToTask(Long taskId, Long workerId) {
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

    public Page<TaskWithUserDTO> getAllTaskWithUser(Pageable pageable) {
        Page<Task> tasksPaginated = repository.findAll(pageable);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasksPaginated.getContent());
        return new PageImpl<>(result, pageable, tasksPaginated.getTotalElements());
    }

    public Page<TaskWithUserDTO> findTasks(TaskSearchDTO taskSearchDTO,
                                           Pageable pageable) {
        String taskId = taskSearchDTO.getTaskId() != null ? String.valueOf(taskSearchDTO.getTaskId()) : null;
        String status = taskSearchDTO.getStatusTask() != null ? String.valueOf(taskSearchDTO.getStatusTask().ordinal()) : null;
        String worker = taskSearchDTO.getWorkerFio().equals("") ? null : (taskSearchDTO.getWorkerFio());
        String category = taskSearchDTO.getCategory() != null ? (taskSearchDTO.getCategory()) : null;
        Page<Task> tasksPaginated = taskRepository.searchTasks(taskId,
                taskSearchDTO.getNameTask(),
                taskSearchDTO.getUserFio(),
                worker,
                category,
                status,
                pageable);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasksPaginated.getContent());
        return new PageImpl<>(result, pageable, tasksPaginated.getTotalElements());
    }

    //Создание заявкии с файлом
    public TaskDTO create(final TaskDTO taskDTO,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file);
        taskDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        taskDTO.setCreatedWhen(LocalDateTime.now());
        taskDTO.setCreateDate(LocalDate.now());
        taskDTO.setEndDate(LocalDate.now().plusDays(1L)); //времено так, далее обработка
        taskDTO.setFiles(fileName);
        return mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public TaskDTO create(final TaskDTO taskDTO) {
        taskDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        taskDTO.setCreatedWhen(LocalDateTime.now());
        taskDTO.setCreateDate(LocalDate.now());
        taskDTO.setEndDate(LocalDate.now().plusDays(1L)); //времено так, далее обработка
        return mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    @Override
    public void delete(Long id) throws MyDeleteException {
        Task task = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Заявки с заданным ID=" + id + " не существует"));
        markAsDeleted(task);
        repository.save(task);
    }

    public void restore(Long objectId) {
        Task task = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Заявки с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(task);
        repository.save(task);
    }

    public TaskDTO update(final TaskDTO taskDTO,
                          MultipartFile file) {
        String fileName = FileHelper.createFile(file);
        taskDTO.setFiles(fileName);
        return mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public Page<TaskWithUserDTO> findAllTaskByLogin(String login, PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllTaskByLogin(login, pageRequest);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasks.getContent());
        return new PageImpl<>(result, pageRequest, tasks.getTotalElements());
    }

    public void updateTaskForWorking(TaskDTO taskDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskDTO.setStatusTask(StatusTask.AT_WORK);
        taskDTO.setEndDate(LocalDate.now().plusDays(4L));
        taskDTO.setWorkerId(Long.valueOf(customUserDetails.getUserId()));
        log.info("taskDTO " + taskDTO);
        mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }
}
