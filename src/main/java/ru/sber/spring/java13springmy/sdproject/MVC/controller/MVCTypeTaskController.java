package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.service.TypeTaskService;

import java.util.List;
@Hidden
@Controller
@RequestMapping("typetask")
public class MVCTypeTaskController {
    private final TypeTaskService typeTaskService;

    public MVCTypeTaskController(TypeTaskService typeTaskService){
        this.typeTaskService = typeTaskService;
    }

    @GetMapping("")
    public String getAll(Model model){
        List<TypeTaskDTO> typeTaskDTOS = typeTaskService.listAll();
        model.addAttribute("typeTask", typeTaskDTOS);
        return "typetask/viewAllTypeTask";
    }
    @GetMapping("/add")
    public String create(){
        return "typetask/addTypeTask";
    }
    @PostMapping("/add")
    public String create(@ModelAttribute("typeTaskForm") TypeTaskDTO typeTaskDTO){
        typeTaskService.create(typeTaskDTO);
        return "redirect:/typetask";
    }

}
