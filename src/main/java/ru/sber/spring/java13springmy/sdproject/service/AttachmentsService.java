package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.AttachmentsDTO;
import ru.sber.spring.java13springmy.sdproject.dto.AttachmentsWithTaskDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.AttachmentsMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.AttachmentsWithTaskMapper;
import ru.sber.spring.java13springmy.sdproject.model.Attachments;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.AttachmentsRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.List;

@Service
public class AttachmentsService extends GenericService<Attachments, AttachmentsDTO> {

    private final AttachmentsRepository attachmentsRepository;
    private final TaskRepository taskRepository;
    private final AttachmentsWithTaskMapper attachmentsWithTaskMapper;
    protected AttachmentsService(AttachmentsRepository attachmentsRepository, AttachmentsMapper attachmentsMapper,
                                 TaskRepository taskRepository, AttachmentsWithTaskMapper attachmentsWithTaskMapper){
        super(attachmentsRepository, attachmentsMapper);
        this.attachmentsRepository = attachmentsRepository;
        this.taskRepository = taskRepository;
        this.attachmentsWithTaskMapper = attachmentsWithTaskMapper;
    }

    public AttachmentsDTO addTaskToAttachment(Long attachmentId, Long taskId) {
        Attachments attachments = attachmentsRepository.findById(attachmentId)
                .orElseThrow(() -> new NotFoundException("Вложение с id " + attachmentId + " не найдено"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Заявка с id " + taskId + " не найдена"));
        task.getAttachments().add(attachments);
        return mapper.toDto(attachmentsRepository.save(attachments));
    }

    public List<AttachmentsWithTaskDTO> getAllAttachmentsWithTask() {
        return attachmentsWithTaskMapper.toDTOs(attachmentsRepository.findAll());
    }
}
