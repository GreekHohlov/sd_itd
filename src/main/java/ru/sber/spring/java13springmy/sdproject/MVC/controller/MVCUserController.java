package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.service.UserService;

import java.util.List;

@Controller
@RequestMapping("users")
public class MVCUserController {
    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("")
    public String getAll(Model model) {
        List<UserDTO> result = userService.listAll();
        model.addAttribute("users", result);
        return "users/viewAllUser";
    }
    //Рисует форму создания
    @GetMapping("/add")
    public String create() {
        return "users/addUser";
    }

    // Примит данные о созданном *** и передаст в БД
    // Потом вернёт нас на страницу со всеми ***
    @PostMapping("/add")
    public String create(@ModelAttribute("userForm") UserDTO userDTO) {
        userService.create(userDTO);
        return "redirect:/users";
    }
}
