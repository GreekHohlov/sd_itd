package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.spring.java13springmy.sdproject.dto.*;
import ru.sber.spring.java13springmy.sdproject.mapper.CategoryMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TypeTaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.UserMapper;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.service.CategoryService;
import ru.sber.spring.java13springmy.sdproject.service.TaskService;

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
    public String getAll(Model model) {
        List<TaskWithUserDTO> taskWithUserDTOList = taskService.getAllTaskWithUser();
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
        List<UserDTO> workerDTOs = userMapper.toDTOs(userRepository.findAll());
        List<TypeTaskDTO> typeTaskDTOs = typeTaskMapper.toDTOs(typeTaskRepository.findAll());
        List<CategoryDTO> categoryDTOs = categoryMapper.toDTOs(categoryRepository.findAll());
        model.addAttribute("workerForm", workerDTOs);
        model.addAttribute("typeTaskForm", typeTaskDTOs);
        model.addAttribute("categoryForm", categoryDTOs);
        return "task/addTask";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("taskForm") TaskDTO taskDTO,
                         @ModelAttribute("user") Long workerId,
                         @ModelAttribute("nameType") Long typeTaskId,
                         @ModelAttribute("category") Long categoryId,
                         @RequestParam MultipartFile file
    ) {
        taskDTO.setTypeTaskId(typeTaskId);
        taskDTO.setCategoryId(categoryId);
        taskDTO.setWorkerId(workerId);

        if (file != null && file.getSize() > 0) {
            taskService.create(taskDTO, file);
        } else {
            taskService.create(taskDTO);
        }
        return "redirect:/task";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        List<TypeTaskDTO> typeTaskDTOs = typeTaskMapper.toDTOs(typeTaskRepository.findAll());
        List<CategoryDTO> categoryDTOs = categoryMapper.toDTOs(categoryRepository.findAll());
        List<UserDTO> workerDTOs = userMapper.toDTOs(userRepository.findAll());
        List<TaskWithUserDTO> taskWithUserDTOList = taskService.getAllTaskWithUser();
        model.addAttribute("workerForm", workerDTOs);
        model.addAttribute("typeTaskForm", typeTaskDTOs);
        model.addAttribute("categotyForm", categoryDTOs);
        model.addAttribute("taskU", taskWithUserDTOList);
        model.addAttribute("task", taskService.getOne(id));
        return "task/updateTask";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("taskForm") TaskDTO taskDTO,
                         @ModelAttribute("nameType") Long typeTaskId,
                         @ModelAttribute("category") Long categoryId,
                         @ModelAttribute("worker") Long workerId,
                         @ModelAttribute("User") Long userId,
                         @RequestParam MultipartFile file) {
        taskDTO.setCategoryId(categoryId);
        taskDTO.setTypeTaskId(typeTaskId);
        taskDTO.setWorkerId(workerId);
        taskDTO.setUserId(userId);
        if (file != null && file.getSize() > 0) {
            taskService.create(taskDTO, file);
        } else {
            taskService.create(taskDTO);
        }
        return "redirect:/task";
    }

    @PostMapping("/search")
    public String searchTask(@ModelAttribute("taskSearchForm") TaskSearchDTO taskSearchDTO,
                             Model model) {
        log.info("данные переданы" + taskSearchDTO.toString());
        List<String> categoryDTOS = categoryService.getName(categoryMapper.toDTOs(categoryRepository.findAll()));
        model.addAttribute("taskSearch", categoryDTOS);
        model.addAttribute("task", taskService.findTasks(taskSearchDTO));
        return "task/viewAllTask";
    }

}
