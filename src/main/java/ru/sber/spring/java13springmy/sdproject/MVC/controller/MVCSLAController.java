package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;

import ru.sber.spring.java13springmy.sdproject.service.SLAService;

import java.util.List;

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

    //Рисует форму создания
    @GetMapping("/add")
    public String create() {
        return "sla/addSLA";
    }

    // Примит данные о созданном фильме и передаст в БД
    // Потом вернёт нас на страницу со всеми фильмами
    @PostMapping("/add")
    public String create(@ModelAttribute("slaForm") SLADTO sladto) {
        slaService.create(sladto);
        return "redirect:/sla";
    }
}
