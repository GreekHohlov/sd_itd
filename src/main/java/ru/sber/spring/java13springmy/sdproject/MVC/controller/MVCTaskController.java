package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.service.TaskService;

import java.util.List;

@Hidden
@Controller
@RequestMapping("/task")
public class MVCTaskController {
    private final TaskService taskService;

    public MVCTaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("")
    public String getAll(Model model){
        List<TaskWithUserDTO> taskWithUserDTOList = taskService.getAllTaskWithUser();
        model.addAttribute("task", taskWithUserDTOList);
        return "task/viewAllTask";
    }

    @GetMapping("/add")
    public String create(){
        return "task/addTask";
    }
    @PostMapping("/add")
    public String create(@ModelAttribute("taskForm")TaskDTO taskDTO) {
        taskService.create(taskDTO);
        return "redirect:/task";
    }
}
