package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.service.SLAService;

import java.util.List;
@Hidden
@Controller
@RequestMapping("sla")
public class MVCSLAController {
    private final SLAService slaService;

    public MVCSLAController(SLAService slaService) {
        this.slaService = slaService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        List<SLADTO> result = slaService.listAll();
        model.addAttribute("SLA", result);
        return "sla/viewAllSLA";
    }

     @GetMapping("/add")
    public String create(Model model) {
        return "sla/addSLA";
    }

       @PostMapping("/add")
    public String create(@ModelAttribute("slaForm") SLADTO sladto) {
        slaService.create(sladto);
        return "redirect:/sla";
    }

    @GetMapping("/deleteSoft/{id}")
    public String deleteSoft(@PathVariable Long id) throws MyDeleteException {
        slaService.deleteSoft(id);
        return "redirect:/sla";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        slaService.delete(id);
        return "redirect:/sla";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        slaService.restore(id);
        return "redirect:/sla";
    }
}
