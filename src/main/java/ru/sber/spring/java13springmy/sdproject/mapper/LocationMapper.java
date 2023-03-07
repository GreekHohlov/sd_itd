package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.Location;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocationMapper extends GenericMapper<Location, LocationDTO> {
    private final UserRepository userRepository;

    protected LocationMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper, Location.class, LocationDTO.class);
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Location.class, LocationDTO.class)
                .addMappings(m -> m.skip(LocationDTO::setUsersIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(LocationDTO.class, Location.class)
                .addMappings(m -> m.skip(Location::setUsers)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(LocationDTO source, Location destination) {
        if (!Objects.isNull(source.getUsersIds())) {
            destination.setUsers(new HashSet<>(userRepository.findAllById(source.getUsersIds())));
        } else {
            destination.setUsers(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(Location source, LocationDTO destination) {
        destination.setUsersIds(getIds(source));
    }

    @Override
    protected Set<Long> getIds(Location entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getUsers())
                ? null
                : entity.getUsers().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
