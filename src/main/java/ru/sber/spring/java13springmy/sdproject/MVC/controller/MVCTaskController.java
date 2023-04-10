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
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.service.CategoryService;
import ru.sber.spring.java13springmy.sdproject.service.HistoryService;
import ru.sber.spring.java13springmy.sdproject.service.TaskService;
import ru.sber.spring.java13springmy.sdproject.service.UserService;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    private final UserService userService;
    private final HistoryService historyService;

    public MVCTaskController(TaskService taskService,
                             CategoryMapper categoryMapper,
                             CategoryRepository categoryRepository,
                             CategoryService categoryService,
                             UserMapper userMapper,
                             UserRepository userRepository,
                             TypeTaskMapper typeTaskMapper,
                             TypeTaskRepository typeTaskRepository,
                             UserService userService,
                             HistoryService historyService) {
        this.taskService = taskService;
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.typeTaskMapper = typeTaskMapper;
        this.typeTaskRepository = typeTaskRepository;
        this.userService = userService;
        this.historyService = historyService;
    }

    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<TaskWithUserDTO> result;
        if (role.equals("[ROLE_USER]")) {
            String login = userRepository.findUsersByLogin(SecurityContextHolder.getContext()
                    .getAuthentication().getName()).getLogin();
            result = taskService.findAllTaskByLogin(login, pageRequest);
        } else if (role.equals("[ROLE_EXECUTOR]") || role.equals("[ROLE_MAIN_EXECUTOR]")) {
            result = taskService.findAllNotDeletedTask(pageRequest);
            Long userId = Long.valueOf(((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUserId());
            model.addAttribute("userId", userId);
        } else {
            result = taskService.getAllTaskWithUser(pageRequest);
        }
        List<String> categoryDTOS = categoryService.getName(categoryMapper.toDTOs(categoryRepository.findAll()));
        model.addAttribute("taskSearch", categoryDTOS);
        model.addAttribute("task", result);
        return "task/viewAllTask";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!role.equals("[ROLE_ADMIN]")) {
            Long userId = Long.valueOf(((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUserId());
            model.addAttribute("userId", userId);
        }
        model.addAttribute("task", taskService.getTaskWithUser(id));
        return "task/viewTask";
    }
    @GetMapping("history/{id}")
    public String history(@PathVariable Long id,
                          Model model) {
        List<HistoryDTO> historyDTO = historyService.findAllByTaskId(id);
        model.addAttribute("historyForm", historyDTO);
        model.addAttribute("task", taskService.getOne(id));
        return "task/viewTaskHistory";
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
                         @RequestParam MultipartFile file
    ) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!role.equals("[ROLE_ADMIN]")) {
            taskDTO.setUserId(userRepository.findUsersByLogin(SecurityContextHolder.getContext()
                    .getAuthentication().getName()).getId());
            if (file != null && file.getSize() > 0) {
                taskService.create(taskDTO, file);
            } else {
                taskService.create(taskDTO);
            }
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
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
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
        log.info("UPDATE_POSTMAPPING_TASK_DTO: " + taskDTO);

        if (file != null && file.getSize() > 0) {
            log.info("UPDATE_WITH_FILE");
            taskService.update(taskDTO, file, taskService.getOne(taskDTO.getId()));
        } else {
            log.info("UPDATE_WITHOUT_FILE");
            taskService.update(taskDTO, taskService.getOne(taskDTO.getId()));
        }
        return "redirect:/task";
    }

    @PostMapping("/search")
    public String searchTask(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "5") int pageSize,
                             @ModelAttribute("taskSearchForm") TaskSearchDTO taskSearchDTO,
                             Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        List<String> categoryDTOS = categoryService.getName(categoryMapper.toDTOs(categoryRepository.findAll()));
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_USER]")) {
            taskSearchDTO.setUserFio(userRepository.findUsersByLogin(SecurityContextHolder.getContext()
                    .getAuthentication().getName()).getLastName());
        }
        Page<TaskWithUserDTO> result = taskService.findTasks(taskSearchDTO, pageRequest);
        model.addAttribute("task", result);
        model.addAttribute("taskSearch", categoryDTOS);
        return "task/viewAllTask";
    }

    @GetMapping("/search/myTask")
    public String myTask(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        log.info("/search/myTask");
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setUserFio(userService.getOne(Long.valueOf(((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUserId())).getLastName());
        return searchTask(page, pageSize, taskSearchDTO, model);
    }

    @GetMapping("/search/assignToMe")
    public String assignToMe(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "5") int pageSize,
                             Model model) {
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setWorkerFio(userService.getOne(Long.valueOf(((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUserId())).getLastName());
        return searchTask(page, pageSize, taskSearchDTO, model);
    }

    @GetMapping("/search/noAssign")
    public String noAssign(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "5") int pageSize,
                           Model model) {
        TaskSearchDTO taskSearchDTO = new TaskSearchDTO();
        taskSearchDTO.setWorkerFio(userService.getUserByLogin("A_service").getLastName() + " " +
                userService.getUserByLogin("A_service").getFirstName());
        return searchTask(page, pageSize, taskSearchDTO, model);
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

    @GetMapping("/takeTask/{id}")
    public String takeTask(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getOne(id);
        taskService.updateTaskForWorking(taskDTO);
        return "redirect:/task";
    }

    @GetMapping("/stopTask/{id}")
    public String stopTask(@PathVariable Long id,
                           Model model) {
        model.addAttribute("reasonForm", taskService.getTaskWithUser(id));
        log.info("я остановил заявку. GET_MAPPING" + taskService.getTaskWithUser(id).toString());
        return "/task/stopTask";
    }

    @PostMapping("/stopTask")
    public String stopTask(@ModelAttribute("taskStopForm") TaskDTO taskDTO) {
        TaskDTO task = taskService.getOne(taskDTO.getId());
        task.setDecision(taskDTO.getDecision());
        taskService.updateTaskForStop(task);
        return "redirect:/task";
    }

    @GetMapping("/executeTask/{id}")
    public String executeTask(@PathVariable Long id,
                              Model model) {
        model.addAttribute("execForm", taskService.getTaskWithUser(id));
        log.info("я execute заявку");
        return "task/executeTask";
    }

    @PostMapping("/executeTask")
    public String executeTask(@ModelAttribute("taskExecForm") TaskDTO taskDTO) {
        TaskDTO task = taskService.getOne(taskDTO.getId());
        task.setDecision(taskDTO.getDecision());
        taskService.updateTaskForExecute(task);
        return "redirect:/task";
    }
    @GetMapping("/unstopTask/{id}")
    public String unstopTask(@PathVariable Long id) {
        taskService.updateTaskUnstop(taskService.getOne(id));
        return "redirect:/task";
    }
    @GetMapping("/noexecuteTask/{id}")
    public String noexecuteTask(@PathVariable Long id) {
        taskService.updateTaskUnstop(taskService.getOne(id));
        return "redirect:/task";
    }

    @GetMapping("/closeTask/{id}")
    public String closeTask(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getOne(id);
        taskService.closeTaskForWorking(taskDTO);
        return "redirect:/task";
    }
}