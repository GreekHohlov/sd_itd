package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
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

    protected TaskService(TaskRepository taskRepository,
                          TaskMapper taskMapper,
                          UserRepository userRepository,
                          TaskWithUserMapper taskWithUserMapper) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskWithUserMapper = taskWithUserMapper;
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
//        boolean bookCanBeDeleted = repository.findBookByIdAndBookRentInfosReturnedFalseAndIsDeletedFalse(id) == null;
      //  boolean bookCanBeDeleted = repository.checkBookForDeletion(id);
//        if (bookCanBeDeleted) {

        //TODO Уаляет файл при удалении заявки, назад не востанавливает, пока отключим

//            if (task.getFiles() != null && !task.getFiles().isEmpty()) {
//                FileHelper.deleteFile(task.getFiles());
//            }
            markAsDeleted(task);
            repository.save(task);

//        else {
//            throw new MyDeleteException(Errors.Books.BOOK_DELETE_ERROR);
//        }
    }

    public void restore(Long objectId) {
        Task task= repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Заявки с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(task);
        repository.save(task);
    }
}
