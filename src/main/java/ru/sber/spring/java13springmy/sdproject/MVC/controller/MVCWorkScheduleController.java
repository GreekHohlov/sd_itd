package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.WorkScheduleDTO;
import ru.sber.spring.java13springmy.sdproject.service.WorkScheduleService;

import java.util.List;

@Controller
@RequestMapping("workSchedule")
public class MVCWorkScheduleController {
    private final WorkScheduleService workScheduleService;

    public MVCWorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        List<WorkScheduleDTO> result = workScheduleService.listAll();
        model.addAttribute("workSchedules", result);
        return "workSchedule/viewAllWorkSchedule";
    }

    @GetMapping("/add")
    public String create() {
        return "workSchedule/addWorkSchedule";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("workScheduleForm") WorkScheduleDTO workScheduleDTO) {
        workScheduleService.create(workScheduleDTO);
        return "redirect:/workSchedule";
    }
}
