package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.sber.spring.java13springmy.sdproject.dto.*;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.CategoryMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TypeTaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.UserMapper;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.service.CategoryService;
import ru.sber.spring.java13springmy.sdproject.service.TaskService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static ru.sber.spring.java13springmy.sdproject.constants.UserRoleConstants.ADMIN;

@Hidden
@Controller
@RequestMapping("task")
@Slf4j
public class MVCTaskController {
    private final TaskService taskService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TypeTaskMapper typeTaskMapper;
    private final TypeTaskRepository typeTaskRepository;

    public MVCTaskController(TaskService taskService,
                             CategoryMapper categoryMapper,
                             CategoryRepository categoryRepository,
                             CategoryService categoryService,
                             UserMapper userMapper,
                             UserRepository userRepository,
                             TypeTaskMapper typeTaskMapper,
                             TypeTaskRepository typeTaskRepository) {
        this.taskService = taskService;
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.typeTaskMapper = typeTaskMapper;
        this.typeTaskRepository = typeTaskRepository;
    }

    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        log.info("Роль вошедшего: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Page<TaskWithUserDTO> taskWithUserDTOList = taskService.getAllTaskWithUser(pageRequest);
        List<String> categoryDTOS = categoryService.getName(categoryMapper.toDTOs(categoryRepository.findAll()));
        model.addAttribute("taskSearch", categoryDTOS);
        model.addAttribute("task", taskWithUserDTOList);
        return "task/viewAllTask";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("task", taskService.getTaskWithUser(id));
        return "task/viewTask";
    }

    @GetMapping("/add")
    public String create(Model model) {
        List<UserDTO> workerDTOs = userMapper.toDTOs(userRepository.findUserIsWorker());
        List<TypeTaskDTO> typeTaskDTOs = typeTaskMapper.toDTOs(typeTaskRepository.findAll());
        List<CategoryDTO> categoryDTOs = categoryMapper.toDTOs(categoryRepository.findAll());
        model.addAttribute("workerForm", workerDTOs);
        model.addAttribute("typeTaskForm", typeTaskDTOs);
        model.addAttribute("categoryForm", categoryDTOs);
        return "task/addTask";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("taskForm") TaskDTO taskDTO,
                         @ModelAttribute("user") String workerId,
                         @ModelAttribute("nameType") Long typeTaskId,
                         @ModelAttribute("category") String categoryId,
                         @RequestParam MultipartFile file) {
        if (workerId.equals("default") || workerId.equals("")){
            taskDTO.setWorkerId(1L);
        } else {
            taskDTO.setWorkerId(Long.valueOf(workerId));
        }
        if (categoryId.equals("default") || categoryId.equals("")){
            taskDTO.setCategoryId(1L);
        } else {
            taskDTO.setCategoryId(Long.valueOf(categoryId));
        }
        taskDTO.setTypeTaskId(typeTaskId);
        taskDTO.setUserId(userRepository.findUsersByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        taskDTO.setCreateDate(LocalDate.now());
        taskDTO.setStatusTask(StatusTask.OPEN);
        if (file != null && file.getSize() > 0) {
            taskService.create(taskDTO, file);
        } else {
            taskService.create(taskDTO);
        }
        return "redirect:/task";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        List<TypeTaskDTO> typeTaskDTOs = typeTaskMapper.toDTOs(typeTaskRepository.findAll());
        List<CategoryDTO> categoryDTOs = categoryMapper.toDTOs(categoryRepository.findAll());
        List<UserDTO> workerDTOs = userMapper.toDTOs(userRepository.findUserIsWorker());
        List<UserDTO> userDTOs = userMapper.toDTOs(userRepository.findAll());
        List<TaskWithUserDTO> taskWithUserDTOList = taskService.getAllTaskWithUser();
        log.info("GET_MAPPING. Заявка: " + id);
        log.info("typeTaskDTOs: " + typeTaskDTOs);
        log.info("categoryDTOs: " + categoryDTOs);
        log.info("workerDTOs: " + workerDTOs);
        log.info("userDTOs: " + userDTOs);
        log.info("taskWithUserDTOList: " + taskWithUserDTOList);
        model.addAttribute("workerForm", workerDTOs);
        model.addAttribute("typeTaskForm", typeTaskDTOs);
        model.addAttribute("categoryForm", categoryDTOs);
        model.addAttribute("taskU", userDTOs);
        model.addAttribute("taskWithUser", taskWithUserDTOList);
        model.addAttribute("task", taskService.getOne(id));
        return "task/updateTask";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("taskForm") TaskDTO taskDTO,
                         @ModelAttribute("nameType") String typeTaskId,
                         @ModelAttribute("category") String categoryId,
                         @ModelAttribute("worker") String workerId,
                         @ModelAttribute("user") String userId,
                         @RequestParam MultipartFile file) {
        TaskDTO tempDTO = taskService.getOne(taskDTO.getId());
        if (typeTaskId.equals("default")){
            taskDTO.setTypeTaskId(tempDTO.getTypeTaskId());
        } else {
            taskDTO.setTypeTaskId(Long.valueOf(typeTaskId));
        }
        if (categoryId.equals("default")){
            taskDTO.setCategoryId(tempDTO.getCategoryId());
        } else {
            taskDTO.setCategoryId(Long.valueOf(categoryId));
        }
        if (workerId.equals("default")){
            taskDTO.setWorkerId(tempDTO.getWorkerId());
        } else {
            taskDTO.setWorkerId(Long.valueOf(workerId));
        }
        if (userId.equals("default")){
            taskDTO.setUserId(tempDTO.getUserId());
        } else {
            taskDTO.setUserId(Long.valueOf(userId));
        }
        taskDTO.setCreateDate(LocalDate.now());
        taskDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("DTO_FULL:" + taskDTO);
        if (file != null && file.getSize() > 0) {
            taskService.update(taskDTO, file);
        } else {
            taskService.update(taskDTO);
        }
        return "redirect:/task";
    }

    @PostMapping("/search")
    public String searchTask(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "5") int pageSize,
                             @ModelAttribute("taskSearchForm") TaskSearchDTO taskSearchDTO,
                             Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        List<String> categoryDTOS = categoryService.getName(categoryMapper.toDTOs(categoryRepository.findAll()));
        Page<TaskWithUserDTO> result;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (ADMIN.equalsIgnoreCase(userName)) {
            result = taskService.getAllTasksWithUsers(pageRequest);
        }
        else {
            result = taskService.getAllNotDeletedTasksWithUsers(pageRequest);
        }
       //model.addAttribute("books", result);
        model.addAttribute("taskSearch", categoryDTOS);
        model.addAttribute("task", result);
        return "task/viewAllTask";
    }
    @GetMapping(value = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@Param(value = "taskId") Long taskId) throws IOException {
        TaskDTO taskDTO = taskService.getOne(taskId);
        Path path = Paths.get(taskDTO.getFiles());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(this.headers(path.getFileName().toString()))
                .contentLength(path.toFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private HttpHeaders headers(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        taskService.delete(id);
        return "redirect:/task";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        taskService.restore(id);
        return "redirect:/task";
    }

    @ExceptionHandler({MyDeleteException.class, AccessDeniedException.class})
    public RedirectView handleError(HttpServletRequest req,
                                    Exception ex,
                                    RedirectAttributes redirectAttributes) {
        log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
        redirectAttributes.addFlashAttribute("exception", ex.getMessage());
        return new RedirectView("/task", true);
    }
}
