package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.service.RoleService;

import java.util.List;
@Hidden
@Controller
@RequestMapping("role")
public class MVCRoleController {
    private final RoleService roleService;

    public MVCRoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("")
    public String getAll(Model model) {
        List<RoleDTO> result = roleService.listAll();
        model.addAttribute("roles", result);
        return "role/viewAllRole";
    }
}
