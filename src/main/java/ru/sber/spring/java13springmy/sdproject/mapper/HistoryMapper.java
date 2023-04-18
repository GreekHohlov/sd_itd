package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.HistoryDTO;
import ru.sber.spring.java13springmy.sdproject.model.History;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.Set;

@Component
public class HistoryMapper extends GenericMapper<History, HistoryDTO>{

    private final TaskRepository taskRepository;

    protected HistoryMapper(ModelMapper modelMapper,
                            TaskRepository taskRepository){
        super(modelMapper, History.class, HistoryDTO.class);
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(History.class, HistoryDTO.class)
                .addMappings(m -> m.skip(HistoryDTO::setTaskId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(HistoryDTO.class, History.class)
                .addMappings(m -> m.skip(History::setTask)).setPostConverter(toEntityConverter());
    }
    @Override
    protected void mapSpecificFields(HistoryDTO source, History destination) {
        destination.setTask(taskRepository.findById(source.getTaskId())
                .orElseThrow(() -> new NotFoundException("Заявка не найдена")));
    }

    @Override
    protected void mapSpecificFields(History source, HistoryDTO destination) {
        destination.setTaskId(source.getTask().getId());
    }

    @Override
    protected Set<Long> getIds(History entity) {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }
}
