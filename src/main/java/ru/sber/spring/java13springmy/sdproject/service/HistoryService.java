package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.HistoryDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.HistoryMapper;
import ru.sber.spring.java13springmy.sdproject.model.History;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class HistoryService extends GenericService<History, HistoryDTO>{
    private final HistoryMapper historyMapper;
    private HistoryRepository historyRepository;
    protected HistoryService(HistoryRepository historyRepository,
                             HistoryMapper historyMapper) {
        super(historyRepository, historyMapper);
        this.historyMapper = historyMapper;
        this.historyRepository = historyRepository;
    }

    public List<HistoryDTO> getAllById(Long id) {
        return historyMapper.toDTOs(historyRepository.getAllById(id));
    }
    public HistoryDTO create(TaskDTO taskDTO,
                                 User user, String event){
        HistoryDTO historyDTO = new HistoryDTO();
        log.info("TASK_DTO_ID_TASKDTO: " + taskDTO.getId());
        historyDTO.setTaskId(taskDTO.getId());
        historyDTO.setCreatedWhen(LocalDateTime.now());
        historyDTO.setCreatedBy(user.getLogin());
        historyDTO.setEvent(event + " \n" + user.getLastName() + " " +
                user.getFirstName().charAt(0) + " " +
                user.getMiddleName().charAt(0) + "\n\n");
        log.info("TASK_DTO_HISTORY: " + historyDTO);
        return mapper.toDto(repository.save(mapper.toEntity(historyDTO)));
    }

    public List<HistoryDTO> findAllByTaskId(Long taskId){
        return  historyMapper.toDTOs(historyRepository.findAllByTaskId(taskId));
    }
}
