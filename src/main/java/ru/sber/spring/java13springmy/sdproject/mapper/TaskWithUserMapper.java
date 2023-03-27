package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TaskWithUserDTO;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.Set;

@Component
public class TaskWithUserMapper extends GenericMapper<Task, TaskWithUserDTO> {
    private final UserRepository userRepository;
    private final TypeTaskRepository typeTaskRepository;
    private final CategoryRepository categoryRepository;

    public TaskWithUserMapper(ModelMapper modelMapper, UserRepository userRepository,
                              TypeTaskRepository typeTaskRepository,
                              CategoryRepository categoryRepository) {
        super(modelMapper, Task.class, TaskWithUserDTO.class);
        this.userRepository = userRepository;
        this.typeTaskRepository = typeTaskRepository;
        this.categoryRepository = categoryRepository;
    }
//    @PostConstruct
//    protected void setupMapper() {
//        modelMapper.createTypeMap(Task.class, TaskWithUserDTO.class)
//                .addMappings(m -> m.skip(TaskWithUserDTO::setTypeTaskId)).setPostConverter(toDtoConverter());
//        modelMapper.createTypeMap(TaskWithUserDTO.class, Task.class)
//                .addMappings(m -> m.skip(Task::setTypeTask)).setPostConverter(toEntityConverter());
//    }
    @Override
    protected void mapSpecificFields(TaskWithUserDTO source, Task destination) {
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow());
        destination.setWorker(userRepository.findById(source.getWorkerId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        destination.setTypeTask(typeTaskRepository.findById(source.getTypeTaskId()).orElseThrow());
        destination.setCategory(categoryRepository.findById(source.getCategoryId()).orElseThrow());
    }

    @Override
    protected void mapSpecificFields(Task source, TaskWithUserDTO destination) {
        destination.setUserId(source.getUser().getId());
        destination.setWorkerId(source.getWorker().getId());
        destination.setTypeTaskId(source.getTypeTask().getId());
        destination.setCategoryId(source.getCategory().getId());
    }

    @Override
    protected Set<Long> getIds(Task entity) {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }
}
