package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.model.Role;
import ru.sber.spring.java13springmy.sdproject.repository.RoleRepository;

import java.util.*;

@Component
public class RoleMapper extends GenericMapper<Role, RoleDTO> {
    private final RoleRepository roleRepository;

    protected RoleMapper(ModelMapper modelMapper, RoleRepository roleRepository) {
        super(modelMapper, Role.class, RoleDTO.class);
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Role.class, RoleDTO.class)
                .addMappings(m -> m.skip(RoleDTO::setGroupsIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(RoleDTO.class, Role.class)
                .addMappings(m -> m.skip(Role::setGroups)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(RoleDTO source, Role destination) {
      /*
        if (!Objects.isNull(source.getGroupsIds())) {
            destination.setGroups(new HashSet<>(roleRepository.findAllById(source.getGroupsIds())));
        } else {
            destination.setGroups(Collections.emptySet());
        }
        */



    }

    @Override
    protected void mapSpecificFields(Role source, RoleDTO destination) {
        destination.setGroupsIds(getIds(source));
    }

    @Override
    protected Set<Long> getIds(Role entity) {
        // дописать
        return null;
    }
}
