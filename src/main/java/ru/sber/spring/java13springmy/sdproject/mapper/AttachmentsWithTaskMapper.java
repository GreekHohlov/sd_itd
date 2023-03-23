package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.AttachmentsWithTaskDTO;
import ru.sber.spring.java13springmy.sdproject.model.Attachments;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.Set;

@Component
public class AttachmentsWithTaskMapper extends GenericMapper<Attachments, AttachmentsWithTaskDTO> {
    private final TaskRepository taskRepository;

    public AttachmentsWithTaskMapper(ModelMapper modelMapper,
                                     TaskRepository taskRepository) {
        super(modelMapper, Attachments.class, AttachmentsWithTaskDTO.class);
        this.taskRepository = taskRepository;
    }
    @PostConstruct
    protected void setupMapper(){
        modelMapper.createTypeMap(Attachments.class, AttachmentsWithTaskDTO.class)
                .addMappings(m -> m.skip(AttachmentsWithTaskDTO::setTaskId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(AttachmentsWithTaskDTO.class, Attachments.class)
                .addMappings(m -> m.skip(Attachments::setTask)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(AttachmentsWithTaskDTO source, Attachments destination) {
        destination.setTask(taskRepository.findById(source.getTaskId())
                .orElseThrow(() -> new NotFoundException("Заявка не найдена")));
    }

    @Override
    protected void mapSpecificFields(Attachments source, AttachmentsWithTaskDTO destination) {
        destination.setTaskId(source.getTask().getId());
    }

    @Override
    protected Set<Long> getIds(Attachments entity) {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }
}
