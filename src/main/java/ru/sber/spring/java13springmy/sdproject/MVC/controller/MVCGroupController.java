package ru.sber.spring.java13springmy.sdproject.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.RoleMapper;
import ru.sber.spring.java13springmy.sdproject.repository.RoleRepository;
import ru.sber.spring.java13springmy.sdproject.service.GroupService;
import ru.sber.spring.java13springmy.sdproject.service.RoleService;

import java.util.List;

@Controller
@RequestMapping("groups")
public class MVCGroupController {
    private final GroupService groupService;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final RoleService roleService;


    public MVCGroupController(GroupService groupService, RoleMapper roleMapper, RoleRepository roleRepository, RoleService roleService) {
        this.groupService = groupService;
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
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

    //Рисует форму создания
    @GetMapping("/add")
    public String create() {
//        List<RoleDTO> roleDTOs = roleMapper.toDTOs(roleRepository.findAll());
//        model.addAttribute("roleForm", roleDTOs);
        return "groups/addGroup";
    }

    // Примит данные о созданном *** и передаст в БД
    // Потом вернёт нас на страницу со всеми ***
    @PostMapping("/add")
    public String create(@ModelAttribute("groupForm") GroupDTO groupDTO) {
       // groupDTO.setRole(roleId);
        groupService.create(groupDTO);
        return "redirect:/groups";
    }
}
