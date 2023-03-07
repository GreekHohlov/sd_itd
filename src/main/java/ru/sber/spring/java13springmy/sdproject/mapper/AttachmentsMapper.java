package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.AttachmentsDTO;
import ru.sber.spring.java13springmy.sdproject.model.Attachments;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.Set;
@Component
public class AttachmentsMapper extends GenericMapper<Attachments, AttachmentsDTO> {
    private final TaskRepository taskRepository;
    protected AttachmentsMapper(ModelMapper modelMapper, TaskRepository taskRepository){
        super(modelMapper, Attachments.class, AttachmentsDTO.class);
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Attachments.class, AttachmentsDTO.class)
                .addMappings(m -> m.skip(AttachmentsDTO::setTaskId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(AttachmentsDTO.class, Attachments.class)
                .addMappings(m -> m.skip(Attachments::setTask)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(AttachmentsDTO source, Attachments destination) {
        destination.setTask(taskRepository.findById(source.getTaskId())
                .orElseThrow(() -> new NotFoundException("Заявка не найдена")));
    }

    @Override
    protected void mapSpecificFields(Attachments source, AttachmentsDTO destination) {
        destination.setTaskId(source.getTask().getId());
    }

    @Override
    protected Set<Long> getIds(Attachments entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
