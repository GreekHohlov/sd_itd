package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.RoleMapper;
import ru.sber.spring.java13springmy.sdproject.model.Role;
import ru.sber.spring.java13springmy.sdproject.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService extends GenericService<Role, RoleDTO> {
    protected RoleService (RoleRepository roleRepository, RoleMapper roleMapper) {
        super(roleRepository,roleMapper);
    }

    public List<String> getName(List<RoleDTO> roleDTO) {
        List<String> names = new ArrayList<>();
        for (RoleDTO name: roleDTO) {
            names.add(name.getNameRole());
        }
        return names;
    }
}
