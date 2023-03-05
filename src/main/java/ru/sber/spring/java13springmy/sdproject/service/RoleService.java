package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.RoleMapper;
import ru.sber.spring.java13springmy.sdproject.model.Role;
import ru.sber.spring.java13springmy.sdproject.repository.RoleRepository;

@Service
public class RoleService extends GenericService<Role, RoleDTO> {
    protected RoleService (RoleRepository roleRepository, RoleMapper roleMapper) {
        super(roleRepository,roleMapper);
    }
}
