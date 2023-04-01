package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.TaskCreateDTO;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.Set;
@Component
public class TaskCreateMapper extends GenericMapper<Task, TaskCreateDTO> {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TypeTaskRepository typeTaskRepository;

    protected TaskCreateMapper(ModelMapper modelMapper, CategoryRepository categoryRepository,
                               UserRepository userRepository, TypeTaskRepository typeTaskRepository){
        super(modelMapper, Task.class, TaskCreateDTO.class);
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.typeTaskRepository = typeTaskRepository;
    }

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Task.class, TaskCreateDTO.class)
                .addMappings(m -> m.skip(TaskCreateDTO::setCategoryId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskCreateDTO::setWorkerId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskCreateDTO::setTypeTaskId)).setPostConverter(toDtoConverter());
    }
    @Override
    protected void mapSpecificFields(TaskCreateDTO source, Task destination) {
        destination.setCategory(categoryRepository.findById(source.getCategoryId()).orElseThrow());
        destination.setWorker(userRepository.findById(source.getWorkerId()).orElseThrow());
        destination.setTypeTask(typeTaskRepository.findById(source.getTypeTaskId()).orElseThrow());
    }

    @Override
    protected void mapSpecificFields(Task source, TaskCreateDTO destination) {

    }

    @Override
    protected Set<Long> getIds(Task entity) {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }
}
