package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.model.Role;

import java.util.Set;

@Component
public class RoleMapper extends GenericMapper<Role, RoleDTO> {

    protected RoleMapper(ModelMapper modelMapper) {
        super(modelMapper, Role.class, RoleDTO.class);
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Role.class, RoleDTO.class)
                .addMappings(m -> m.skip(RoleDTO::setGroupsIds)).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapSpecificFields(RoleDTO source, Role destination) {
    }

    @Override
    protected void mapSpecificFields(Role source, RoleDTO destination) {
    }

    @Override
    protected Set<Long> getIds(Role entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
