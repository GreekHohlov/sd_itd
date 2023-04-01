package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.Role;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper extends GenericMapper<Role, RoleDTO> {
    private final GroupRepository groupRepository;

    protected RoleMapper(ModelMapper modelMapper, GroupRepository groupRepository) {
        super(modelMapper, Role.class, RoleDTO.class);
        this.groupRepository = groupRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Role.class, RoleDTO.class)
                .addMappings(m -> m.skip(RoleDTO::setGroupsIds)).setPostConverter(toDtoConverter());
//        modelMapper.createTypeMap(RoleDTO.class, Role.class)
//                .addMappings(m -> m.skip(Role::setGroups)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(RoleDTO source, Role destination) {

//        if (!Objects.isNull(source.getGroupsIds())) {
//            destination.setGroups(new HashSet<>(groupRepository.findAllById(source.getGroupsIds())));
//        } else {
//            destination.setGroups(Collections.emptySet());
//        }
    }

    @Override
    protected void mapSpecificFields(Role source, RoleDTO destination) {
//        destination.setGroupsIds(getIds(source));
    }

    @Override
    protected Set<Long> getIds(Role entity) {
//        return Objects.isNull(entity) || Objects.isNull(entity.getGroups())
//                ? Collections.emptySet()
//                : entity.getGroups().stream()
//                .map(GenericModel::getId)
//                .collect(Collectors.toSet());
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
