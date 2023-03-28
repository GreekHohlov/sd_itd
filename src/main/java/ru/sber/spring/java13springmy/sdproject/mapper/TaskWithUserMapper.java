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
    private final UserMapper userMapper;
    private final TypeTaskMapper typeTaskMapper;
    private final CategoryMapper categoryMapper;


    public TaskWithUserMapper(ModelMapper modelMapper,
                              UserRepository userRepository,
                              TypeTaskRepository typeTaskRepository,
                              CategoryRepository categoryRepository,
                              UserMapper userMapper,
                              TypeTaskMapper typeTaskMapper,
                              CategoryMapper categoryMapper) {
        super(modelMapper, Task.class, TaskWithUserDTO.class);
        this.userRepository = userRepository;
        this.typeTaskRepository = typeTaskRepository;
        this.categoryRepository = categoryRepository;
        this.userMapper = userMapper;
        this.typeTaskMapper = typeTaskMapper;
        this.categoryMapper = categoryMapper;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Task.class, TaskWithUserDTO.class)
                .addMappings(m -> m.skip(TaskWithUserDTO::setUserId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskWithUserDTO::setWorkerId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskWithUserDTO::setStatusTask)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskWithUserDTO::setTypeTaskId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskWithUserDTO::setCategoryId)).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapSpecificFields(TaskWithUserDTO source, Task destination) {
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow());
        destination.setWorker(userRepository.findById(source.getWorkerId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        destination.setTypeTask(typeTaskRepository.findById(source.getTypeTaskId()).orElseThrow());
        destination.setCategory(categoryRepository.findById(source.getCategoryId()).orElseThrow());
        destination.setStatusTask(source.getStatusTask());
    }

    @Override
    protected void mapSpecificFields(Task source, TaskWithUserDTO destination) {
        destination.setUser(userMapper.toDto(source.getUser()));
        destination.setWorker(userMapper.toDto(source.getWorker()));
        destination.setStatusTask((source.getStatusTask()));
        destination.setTypeTask(typeTaskMapper.toDto(source.getTypeTask()));
        destination.setCategory(categoryMapper.toDto(source.getCategory()));
    }

    @Override
    protected Set<Long> getIds(Task entity) {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }
}
