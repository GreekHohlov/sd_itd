package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.AttachmentsDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.AttachmentsMapper;
import ru.sber.spring.java13springmy.sdproject.model.Attachments;
import ru.sber.spring.java13springmy.sdproject.repository.AttachmentsRepository;

@Service
public class AttachmentsService extends GenericService<Attachments, AttachmentsDTO> {
    protected AttachmentsService(AttachmentsRepository attachmentsRepository, AttachmentsMapper attachmentsMapper){
        super(attachmentsRepository, attachmentsMapper);
    }
}
