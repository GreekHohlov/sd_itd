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
import ru.sber.spring.java13springmy.sdproject.dto.TaskSearchDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TaskWithUserMapper;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetails;
import ru.sber.spring.java13springmy.sdproject.utils.FileHelper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TaskService extends GenericService<Task, TaskDTO> {
    private final TypeTaskRepository typeTaskRepository;
    private final TaskRepository taskRepository;
    private final TaskWithUserMapper taskWithUserMapper;
    private final SLAService slaService;
    private final UserRepository userRepository;

    protected TaskService(TaskRepository taskRepository,
                          TaskMapper taskMapper,
                          TaskWithUserMapper taskWithUserMapper,
                          SLAService slaService,
                          TypeTaskRepository typeTaskRepository,
                          UserRepository userRepository) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
        this.taskWithUserMapper = taskWithUserMapper;
        this.slaService = slaService;
        this.typeTaskRepository = typeTaskRepository;
        this.userRepository = userRepository;
    }

    public TaskWithUserDTO getTaskWithUser(Long id) {
        return taskWithUserMapper.toDto(mapper.toEntity(super.getOne(id)));
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
        String worker = taskSearchDTO.getWorkerFio() != null ? (taskSearchDTO.getWorkerFio()) : null;
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
        taskDTO.setUserId(userRepository.findUsersByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        taskDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        taskDTO.setCreatedWhen(LocalDateTime.now());
        taskDTO.setCreateDate(LocalDateTime.now());
        taskDTO.setStatusTask(StatusTask.OPEN);
        taskDTO.setFiles(fileName);
        return mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public TaskDTO create(final TaskDTO taskDTO) {
        taskDTO.setUserId(userRepository.findUsersByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        taskDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        taskDTO.setCreatedWhen(LocalDateTime.now());
        taskDTO.setCreateDate(LocalDateTime.now());
        taskDTO.setStatusTask(StatusTask.OPEN);
        log.info("CREATE TASK_DTO: " + taskDTO);
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

    public TaskDTO update(final TaskDTO taskDTO) {
        TaskDTO task = getOne(taskDTO.getId());
        taskDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        taskDTO.setCreateDate(task.getCreateDate());
        taskDTO.setEndDate(task.getEndDate());
        return mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public TaskDTO update(final TaskDTO taskDTO,
                          MultipartFile file) {
        TaskDTO task = getOne(taskDTO.getId());
        taskDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        String fileName = FileHelper.createFile(file);
        taskDTO.setFiles(fileName);
        taskDTO.setCreateDate(task.getCreateDate());
        taskDTO.setEndDate(task.getEndDate());
        return mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public Page<TaskWithUserDTO> findAllTaskByLogin(String login, PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllTaskByLogin(login, pageRequest);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasks.getContent());
        return new PageImpl<>(result, pageRequest, tasks.getTotalElements());
    }

    public Page<TaskWithUserDTO> findAllNotDeletedTask(PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllNotDeletedTask(pageRequest);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasks.getContent());
        return new PageImpl<>(result, pageRequest, tasks.getTotalElements());
    }

    public Page<TaskWithUserDTO> findAllNotDeletedTask(PageRequest pageRequest) {
        Page<Task> tasks = taskRepository.findAllNotDeletedTask(pageRequest);
        List<TaskWithUserDTO> result = taskWithUserMapper.toDTOs(tasks.getContent());
        return new PageImpl<>(result, pageRequest, tasks.getTotalElements());
    }

    public void updateTaskForWorking(TaskDTO taskDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.isNull(taskDTO.getEndDate())) {
            taskDTO.setStatusTask(StatusTask.AT_WORK);
            taskDTO.setEndDate(LocalDateTime.now().plusHours(slaService.getOne((typeTaskRepository
                    .getReferenceById(taskDTO.getTypeTaskId()).getSla()).getId()).getExecutionTime()));
        }
        taskDTO.setWorkerId(Long.valueOf(customUserDetails.getUserId()));
        mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public void updateTaskForStop(TaskDTO taskDTO) {

        if (!StatusTask.COMPLETED.statusTaskTextDisplay().equals(taskDTO.getStatusTask().statusTaskTextDisplay()) ||
                !StatusTask.CLOSED.statusTaskTextDisplay().equals(taskDTO.getStatusTask().statusTaskTextDisplay()) ||
                !StatusTask.STOPPED.statusTaskTextDisplay().equals(taskDTO.getStatusTask().statusTaskTextDisplay())) {
            taskDTO.setStatusTask(StatusTask.STOPPED);
            taskDTO.setEndDate(null);
        }
        mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public void updateTaskForExecute(TaskDTO taskDTO) {
        if (Objects.isNull(taskDTO.getWorkerId())) {
            taskDTO.setWorkerId(Long.valueOf(((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUserId()));
        }
        log.info("STATUS_BEFORE_EXEC: " + taskDTO.getStatusTask().statusTaskTextDisplay());
        if (!StatusTask.COMPLETED.statusTaskTextDisplay().equals(taskDTO.getStatusTask().statusTaskTextDisplay()) ||
                !StatusTask.CLOSED.statusTaskTextDisplay().equals(taskDTO.getStatusTask().statusTaskTextDisplay())) {
            taskDTO.setStatusTask(StatusTask.COMPLETED);
            taskDTO.setCreatedWhen(LocalDateTime.now());
        }
        mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public void closeTaskForWorking(TaskDTO taskDTO) {
        if (StatusTask.COMPLETED.statusTaskTextDisplay().equals(taskDTO.getStatusTask().statusTaskTextDisplay())) {
            taskDTO.setStatusTask(StatusTask.CLOSED);
            taskDTO.setCreatedWhen(LocalDateTime.now());
        }
        mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }

    public void updateTaskUnstop(TaskDTO taskDTO) {
        taskDTO.setStatusTask(StatusTask.AT_WORK);
        taskDTO.setEndDate(LocalDateTime.now().plusHours(slaService.getOne((typeTaskRepository
                .getReferenceById(taskDTO.getTypeTaskId()).getSla()).getId()).getExecutionTime()));
        mapper.toDto(repository.save(mapper.toEntity(taskDTO)));
    }
}
