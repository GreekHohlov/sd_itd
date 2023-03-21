package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.service.GroupService;

import java.util.List;

@Controller
@RequestMapping("groups")
public class MVCGroupController {
    private final GroupService groupService;

    public MVCGroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping("")
    public String getAll(Model model) {
        List<GroupDTO> result = groupService.listAll();
        model.addAttribute("groups", result);
        return "groups/viewAllGroup";
    }
    //Рисует форму создания
    @GetMapping("/add")
    public String create() {
        return "groups/addGroup";
    }

    // Примит данные о созданном *** и передаст в БД
    // Потом вернёт нас на страницу со всеми ***
    @PostMapping("/add")
    public String create(@ModelAttribute("groupForm") GroupDTO groupDTO) {
        groupService.create(groupDTO);
        return "redirect:/groups";
    }
}
