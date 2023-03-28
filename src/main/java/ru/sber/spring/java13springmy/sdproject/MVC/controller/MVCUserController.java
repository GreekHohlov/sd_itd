package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.service.UserService;

import java.util.List;

import static ru.sber.spring.java13springmy.sdproject.constants.UserRoleConstants.ADMIN;

@Controller
@RequestMapping("/users")
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

    // Примет данные о созданном *** и передаст в БД
    // Потом вернёт нас на страницу со всеми ***
    @PostMapping("/add")
    public String create(@ModelAttribute("userForm") UserDTO userDTO) {
        userService.create(userDTO);
        return "redirect:/users";
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }

    @PostMapping("registration")
    public String registration(@ModelAttribute("userForm") UserDTO userDTO,
                               BindingResult bindingResult) {
        if (userDTO.getLogin().equalsIgnoreCase(ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такой email уже существует");
            return "registration";
        }
        userService.create(userDTO);
        return "redirect:login";
    }
}