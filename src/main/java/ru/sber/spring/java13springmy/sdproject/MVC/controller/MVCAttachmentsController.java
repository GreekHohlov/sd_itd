package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.AttachmentsDTO;
import ru.sber.spring.java13springmy.sdproject.dto.AttachmentsWithTaskDTO;
import ru.sber.spring.java13springmy.sdproject.service.AttachmentsService;

import java.util.List;
@Hidden
@Controller
@RequestMapping("/attachments")
public class MVCAttachmentsController {
    private final AttachmentsService attachmentsService;

    public MVCAttachmentsController(AttachmentsService attachmentsService){
        this.attachmentsService = attachmentsService;
    }

    @GetMapping("")
    public String getAll(Model model){
        List<AttachmentsWithTaskDTO> attachmentsWithTaskDTOS = attachmentsService.getAllAttachmentsWithTask();
        model.addAttribute("attachments", attachmentsWithTaskDTOS);
        return "attachments/viewAllAttachments";
    }
    @GetMapping("/add")
    public String create() {
        return "attachments/addAttachments";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("attachmentsForm")AttachmentsDTO attachmentsDTO) {
        attachmentsService.create(attachmentsDTO);
        return "redirect:/attachments";
    }
}
