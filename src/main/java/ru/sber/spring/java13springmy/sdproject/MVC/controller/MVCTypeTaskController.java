package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.SLAMapper;
import ru.sber.spring.java13springmy.sdproject.repository.SLARepository;
import ru.sber.spring.java13springmy.sdproject.service.TypeTaskService;

import java.util.List;

@Hidden
@Controller
@RequestMapping("typetask")
public class MVCTypeTaskController {
    private final TypeTaskService typeTaskService;
    private final SLARepository slaRepository;
    private final SLAMapper slaMapper;

    public MVCTypeTaskController(TypeTaskService typeTaskService,
                                 SLARepository slaRepository,
                                 SLAMapper slaMapper) {
        this.typeTaskService = typeTaskService;
        this.slaRepository = slaRepository;
        this.slaMapper = slaMapper;
    }

    @GetMapping("")
    public String getAll(Model model) {
        List<TypeTaskDTO> typeTaskDTOS = typeTaskService.listAll();
        model.addAttribute("typeTask", typeTaskDTOS);

        return "typetask/viewAllTypeTask";
    }

    @GetMapping("/add")
    public String create(Model model) {
        List<SLADTO> slaDTOs = slaMapper.toDTOs(slaRepository.findAll());
        model.addAttribute("slaForm", slaDTOs);
        return "typetask/addTypeTask";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("slaForm") TypeTaskDTO typeTaskDTO) {
        typeTaskService.create(typeTaskDTO);
        return "redirect:/typetask";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        List<SLADTO> slaDTOs = slaMapper.toDTOs(slaRepository.findAll());
        TypeTaskDTO typeTaskDTO = typeTaskService.getOne(id);
        System.out.println(typeTaskDTO.toString());
        model.addAttribute("typeForm", typeTaskDTO);
        model.addAttribute("slaForm", slaDTOs);
        return "typetask/updateTypeTask";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("typeId") Long typeId,
                         @ModelAttribute("nameType") String nameType,
                         @ModelAttribute("slaId") Long slaId) {
        TypeTaskDTO typeTaskDTOUpdate = typeTaskService.getOne(typeId);
        typeTaskDTOUpdate.setNameType(nameType);
        typeTaskDTOUpdate.setSlaId(slaId);
        typeTaskService.update(typeTaskDTOUpdate);
        return "redirect:/typetask";
    }

    @GetMapping("/deleteSoft/{id}")
    public String deleteSoft(@PathVariable Long id) {
        typeTaskService.deleteSoft(id);
        return "redirect:/typetask";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        typeTaskService.delete(id);
        return "redirect:/typetask";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        typeTaskService.restore(id);
        return "redirect:/typetask";
    }
}