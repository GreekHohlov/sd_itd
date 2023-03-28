package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;
import ru.sber.spring.java13springmy.sdproject.repository.LocationRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper extends GenericMapper<User, UserDTO> {
    private final LocationRepository locationRepository;
    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;


    protected UserMapper(ModelMapper modelMapper,
                         LocationRepository locationRepository,
                         GroupRepository groupRepository,
                         TaskRepository taskRepository) {
        super(modelMapper, User.class, UserDTO.class);
        this.locationRepository = locationRepository;
        this.groupRepository = groupRepository;
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setLocationId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(UserDTO::setGroupId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(UserDTO::setTasksIds)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(UserDTO::setTasksWorkerIds)).setPostConverter(toDtoConverter());

        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m -> m.skip(User::setLocation)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(User::setGroup)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(User::setTasks)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(User::setTasksWorker)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        destination.setLocation(locationRepository.findById(source.getLocationId().longValue())
                .orElseThrow(() -> new NotFoundException("Локация не найдена")));

        destination.setGroup(groupRepository.findById(source.getGroupId().longValue())
                .orElseThrow(() -> new NotFoundException("Группа не найдена")));

        if (!Objects.isNull(source.getTasksIds())) {
            destination.setTasks(new HashSet<>(taskRepository.findAllById(source.getTasksIds())));
        } else {
            destination.setTasks(Collections.emptySet());
        }
        if (!Objects.isNull(source.getTasksWorkerIds())) {
            destination.setTasksWorker(new HashSet<>(taskRepository.findAllById(source.getTasksWorkerIds())));
        } else {
            destination.setTasksWorker(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setLocationId(source.getLocation().getId().intValue());
        destination.setGroupId(source.getGroup().getId().intValue());
        destination.setTasksIds(getIds(source));
        destination.setTasksWorkerIds(getWorkerIds(source));
    }

    @Override
    protected Set<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTasks())
                ? null
                : entity.getTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }

    protected Set<Long> getWorkerIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTasksWorker())
                ? null
                : entity.getTasksWorker().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}

