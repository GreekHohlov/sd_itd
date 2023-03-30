package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import jakarta.websocket.server.PathParam;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.service.UserService;

import java.util.List;
import java.util.Objects;

import static ru.sber.spring.java13springmy.sdproject.constants.UserRoleConstants.ADMIN;

@Controller
@RequestMapping("/users")
public class MVCUserController {
    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "lastName"));
        Page<UserDTO> result = userService.listAll(pageRequest);
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

    @GetMapping("/remember-password")
    public String rememberPassword() {
        return "users/rememberPassword";
    }
    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userDTO)) {
            return "redirect:/error/error-message?message=Пользователя с данным email не существует!";
        }
        else {
            userService.sendChangePasswordEmail(userDTO);
            return "redirect:/login";
        }
    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userService.changePassword(uuid, userDTO.getPassword());
        return "redirect:/login";
    }
}
