package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.service.RoleService;

import java.util.List;

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
    /*
    //Рисует форму создания
    @GetMapping("/add")
    public String create() {
        return "role/addRole";
    }

    // Примит данные о созданном *** и передаст в БД
    // Потом вернёт нас на страницу со всеми ***
    @PostMapping("/add")
    public String create(@ModelAttribute("roleForm") RoleDTO roleDTO) {
        roleService.create(roleDTO);
        return "redirect:/role";
    }

     */
}
