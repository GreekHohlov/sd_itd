package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.Set;

@Component
public class TaskWithUserMapper extends GenericMapper<Task, TaskWithUserDTO> {
    private final UserRepository userRepository;

    public TaskWithUserMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper, Task.class, TaskWithUserDTO.class);
        this.userRepository = userRepository;
    }
    @PostConstruct
    protected void setupMapper() {
//        modelMapper.createTypeMap(Task.class, TaskWithUserDTO.class)
//                .addMappings(m -> m.skip(TaskWithUserDTO::setUserId)).setPostConverter(toDtoConverter());
//        modelMapper.createTypeMap(TaskWithUserDTO.class, Task.class)
//                .addMappings(m -> m.skip(Task::setUser)).setPostConverter(toEntityConverter());
    }
    @Override
    protected void mapSpecificFields(TaskWithUserDTO source, Task destination) {
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(Task source, TaskWithUserDTO destination) {
        destination.setUserId(source.getUser().getId());
    }

    @Override
    protected Set<Long> getIds(Task entity) {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }
}
