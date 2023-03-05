package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;
import ru.sber.spring.java13springmy.sdproject.repository.SLARepository;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TypeTaskMapper extends GenericMapper<TypeTask, TypeTaskDTO>{
    private final TaskRepository taskRepository;
    private final SLARepository slaRepository;
    protected TypeTaskMapper(ModelMapper modelMapper, TaskRepository taskRepository, SLARepository slaRepository){
        super(modelMapper, TypeTask.class, TypeTaskDTO.class);
        this.taskRepository = taskRepository;
        this.slaRepository = slaRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(TypeTask.class, TypeTaskDTO.class)
                .addMappings(m -> m.skip(TypeTaskDTO::setTaskIds)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TypeTaskDTO::setSlaId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TypeTaskDTO.class, TypeTask.class)
                .addMappings(m -> m.skip(TypeTask::setTasks)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(TypeTask::setSla));
    }
    @Override
    protected void mapSpecificFields(TypeTaskDTO source, TypeTask destination) {
        destination.setSla(slaRepository.findById(source.getSlaId()).orElseThrow(() -> new NotFoundException("SLA не найден")));
        if(!Objects.isNull(source.getTaskIds())){
            destination.setTasks(new HashSet<>(taskRepository.findAllById(source.getTaskIds())));
        }
        else {
            destination.setTasks(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(TypeTask source, TypeTaskDTO destination) {
        destination.setTaskIds(getIds(source));
        destination.setSlaId(source.getSla().getId());
    }

    @Override
    protected Set<Long> getIds(TypeTask entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTasks())
                ? Collections.emptySet()
                :entity.getTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
