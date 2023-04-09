package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import jakarta.websocket.server.PathParam;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.sber.spring.java13springmy.sdproject.constants.Errors;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.GroupMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.LocationMapper;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;
import ru.sber.spring.java13springmy.sdproject.repository.LocationRepository;
import ru.sber.spring.java13springmy.sdproject.service.UserService;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetails;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ru.sber.spring.java13springmy.sdproject.constants.UserRoleConstants.ADMIN;

@Controller
@Hidden
@Slf4j
@RequestMapping("/users")
public class MVCUserController {
    private final UserService userService;
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    public MVCUserController(UserService userService,
                             GroupMapper groupMapper,
                             GroupRepository groupRepository,
                             LocationMapper locationMapper,
                             LocationRepository locationRepository) {
        this.userService = userService;
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
    }

    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "lastName"));
        Page<UserDTO> result = userService.listAll(pageRequest);
        model.addAttribute("users", result);
        return "users/viewAllUsers";
    }

    @GetMapping("/add")
    public String create() {
        return "users/addUser";
    }

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
        } else {
            userService.sendChangePasswordEmail(userDTO);
            return "redirect:/login";
        }
    }

    @GetMapping("/change-password/user")
    public String changePassword(Model model) {
        log.info("GetMapping/change-password/user");
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        UUID uuid = UUID.randomUUID();
        userDTO.setChangePasswordToken(uuid.toString());
        userService.update(userDTO);
        model.addAttribute("uuid", uuid);

        return "users/changePassword";
    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        log.info("GetMapping/change-password");
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        log.info("PostMapping/change-password");
        userService.changePassword(uuid, userDTO.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Integer id,
                              Model model) throws AuthException {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.isNull(customUserDetails.getUserId())) {
            if (!ADMIN.equalsIgnoreCase(customUserDetails.getUsername())) {
                if (!id.equals(customUserDetails.getUserId())) {
                    throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
                }
            }
        }
        model.addAttribute("user", userService.getOne(Long.valueOf(id)));
        return "profile/viewProfile";
    }

    @ExceptionHandler(AuthException.class)
    public RedirectView handleErrorAuth(HttpServletRequest req,
                                        Exception ex,
                                        RedirectAttributes redirectAttributes) {
        log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
        redirectAttributes.addFlashAttribute("exception", ex.getMessage());
        return new RedirectView("/users/list", true);
    }

    @GetMapping("/profile/update/{id}")
    public String updateProfile(@PathVariable Integer id,
                                Model model) throws AuthException {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<GroupDTO> groupDTOS = groupMapper.toDTOs(groupRepository.findAll());
        List<LocationDTO> locationDTOS = locationMapper.toDTOs(locationRepository.findAll());
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!id.equals(customUserDetails.getUserId()) && (!role.equals("[ROLE_ADMIN]"))) {
            throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
        }
        model.addAttribute("userForm", userService.getOne(Long.valueOf(id)));
        model.addAttribute("groupForm", groupDTOS);
        model.addAttribute("locationForm", locationDTOS);
        return "profile/updateProfile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("userForm") UserDTO userDTOFromUpdateForm,
                                BindingResult bindingResult) {
        UserDTO userEmailDuplicated = userService.getUserByEmail(userDTOFromUpdateForm.getEmail());
        UserDTO foundUser = userService.getOne(userDTOFromUpdateForm.getId());
        if (userEmailDuplicated != null && !Objects.equals(userEmailDuplicated.getEmail(), foundUser.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Такой email уже существует");
            return "profile/updateProfile";
        }
        foundUser.setFirstName(userDTOFromUpdateForm.getFirstName());
        foundUser.setLastName(userDTOFromUpdateForm.getLastName());
        foundUser.setMiddleName(userDTOFromUpdateForm.getMiddleName());
        foundUser.setEmail(userDTOFromUpdateForm.getEmail());
        foundUser.setPhone(userDTOFromUpdateForm.getPhone());
        foundUser.setGroupId(userDTOFromUpdateForm.getGroupId());
        foundUser.setLocationId(userDTOFromUpdateForm.getLocationId());
        userService.update(foundUser);
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (role.equals("[ROLE_USER]")) {
            return "redirect:/users/profile/" + userDTOFromUpdateForm.getId();
        } else {
            return "redirect:/users";
        }
    }

    @GetMapping("/list")
    public String listAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "5") int pageSize,
                               @ModelAttribute(value = "exception") String exception,
                               Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "lastName"));
        Page<UserDTO> userPage = userService.listAll(pageRequest);
        model.addAttribute("users", userPage);
        model.addAttribute("exception", exception);
        return "users/viewAllUsers";
    }

    @PostMapping("/search")
    public String searchUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @ModelAttribute("userSearchForm") UserDTO userDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "first_name"));
        model.addAttribute("users", userService.findUsers(userDTO, pageRequest));
        return "users/viewAllUsers";
    }

    @GetMapping("/add-executor")
    public String addLibrarianPage(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "users/addExecutor";
    }

    @PostMapping("add-executor")
    public String addLibrarian(@ModelAttribute("userForm") UserDTO userDTO,
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
        return "redirect:/users/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        userService.delete(id);
        return "redirect:/users/list";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        userService.restore(id);
        return "redirect:/users/list";
    }
}
