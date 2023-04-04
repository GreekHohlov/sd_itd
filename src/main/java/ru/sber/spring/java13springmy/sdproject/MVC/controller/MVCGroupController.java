package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.service.GroupService;
import ru.sber.spring.java13springmy.sdproject.service.RoleService;

import java.util.List;
@Hidden
@Controller
@RequestMapping("groups")
public class MVCGroupController {
    private final GroupService groupService;
    private final RoleService roleService;

    public MVCGroupController(GroupService groupService,
                              RoleService roleService) {
        this.groupService = groupService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        List<GroupDTO> result = groupService.listAll();
        List<RoleDTO> roleDTOs = roleService.listAll();
        model.addAttribute("roleForm", roleDTOs);
        model.addAttribute("groups", result);
        return "groups/viewAllGroup";
    }

    @GetMapping("/add")
    public String create() {
        return "groups/addGroup";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("groupForm") GroupDTO groupDTO) {
        groupService.create(groupDTO);
        return "redirect:/groups";
    }

    @GetMapping("/deleteSoft/{id}")
    public String deleteSoft(@PathVariable Long id) throws MyDeleteException {
        groupService.deleteSoft(id);
        return "redirect:/groups";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        groupService.delete(id);
        return "redirect:/groups";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        groupService.restore(id);
        return "redirect:/groups";
    }
}
