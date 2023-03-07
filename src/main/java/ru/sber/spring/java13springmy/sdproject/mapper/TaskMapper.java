package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.AttachmentsRepository;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskMapper extends GenericMapper<Task, TaskDTO> {
    private final AttachmentsRepository attachmentsRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TypeTaskRepository typeTaskRepository;
    protected TaskMapper(ModelMapper modelMapper, AttachmentsRepository attachmentsRepository,
                         CategoryRepository categoryRepository, UserRepository userRepository,
                         TypeTaskRepository typeTaskRepository){
        super(modelMapper, Task.class, TaskDTO.class);
        this.attachmentsRepository = attachmentsRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.typeTaskRepository = typeTaskRepository;
    }

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Task.class, TaskDTO.class)
                .addMappings(m -> m.skip(TaskDTO::setAttachmentsIds)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskDTO::setCategoryId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskDTO::setUserId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskDTO::setTypeTaskId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TaskDTO.class, Task.class)
                .addMappings(m -> m.skip(Task::setAttachments)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Task::setCategory)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Task::setUser)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Task::setTypeTask)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(TaskDTO source, Task destination) {
        if(!Objects.isNull(source.getAttachmentsIds())){
            destination.setAttachments(new HashSet<>(attachmentsRepository.findAllById(source.getAttachmentsIds())));
        }
        else {
            destination.setAttachments(Collections.emptySet());
        }
        destination.setCategory(categoryRepository.findById(source.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Категория не найдена")));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь не найден")));
        destination.setTypeTask(typeTaskRepository.findById(source.getTypeTaskId())
                .orElseThrow(() -> new NotFoundException("Тип заявки не найден")));
    }

    @Override
    protected void mapSpecificFields(Task source, TaskDTO destination) {
        destination.setAttachmentsIds(getIds(source));
        destination.setCategoryId(source.getCategory().getId());
        destination.setUserId(source.getUser().getId());
        destination.setTypeTaskId(source.getTypeTask().getId());
    }

    @Override
    protected Set<Long> getIds(Task entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getAttachments())
                ? Collections.emptySet()
                : entity.getAttachments().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
