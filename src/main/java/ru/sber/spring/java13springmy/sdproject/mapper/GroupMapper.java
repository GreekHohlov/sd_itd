package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GroupMapper extends GenericMapper<Group, GroupDTO> {
    private final UserRepository userRepository;

    protected GroupMapper(ModelMapper modelMapper,
                          UserRepository userRepository) {
        super(modelMapper, Group.class, GroupDTO.class);
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Group.class, GroupDTO.class)
                .addMappings(m -> m.skip(GroupDTO::setUsersIds)).setPostConverter(toDtoConverter());

        modelMapper.createTypeMap(GroupDTO.class, Group.class)
                .addMappings(m -> m.skip(Group::setUser)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(GroupDTO source, Group destination) {

        if (!Objects.isNull(source.getUsersIds())) {
            destination.setUser(new HashSet<>(userRepository.findAllById(source.getUsersIds())));
        } else {
            destination.setUser(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(Group source, GroupDTO destination) {
        destination.setUsersIds(getIds(source));
    }

    @Override
    protected Set<Long> getIds(Group entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getUser())
                ? null
                : entity.getUser().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
