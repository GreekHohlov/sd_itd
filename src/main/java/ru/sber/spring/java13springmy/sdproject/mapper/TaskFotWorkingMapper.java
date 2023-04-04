package ru.sber.spring.java13springmy.sdproject.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.TaskFotWorkingDTO;
import ru.sber.spring.java13springmy.sdproject.model.Task;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.Objects;
import java.util.Set;

@Component
public class TaskFotWorkingMapper extends GenericMapper<Task, TaskFotWorkingDTO> {
    private final UserRepository userRepository;

    public TaskFotWorkingMapper(ModelMapper modelMapper,
                                UserRepository userRepository) {
        super(modelMapper, Task.class, TaskFotWorkingDTO.class);
        this.userRepository = userRepository;

    }
    public void setupMapper(){
        modelMapper.createTypeMap(Task.class, TaskFotWorkingDTO.class)
            .addMappings(m -> m.skip(TaskFotWorkingDTO::setWorkerId)).setPostConverter(toDtoConverter());

    }
    @Override
    protected void mapSpecificFields(TaskFotWorkingDTO source, Task destination) {
        if (!Objects.isNull(source.getWorkerId())){
            destination.setWorker(userRepository.findById(source.getWorkerId()).orElseThrow());
        } else {
            destination.setWorker((userRepository.findById(1L)).orElseThrow());
        }
    }

    @Override
    protected void mapSpecificFields(Task source, TaskFotWorkingDTO destination) {
        if (!Objects.isNull(source.getWorker())) {
            destination.setWorkerId(source.getWorker().getId());
        }
    }

    @Override
    protected Set<Long> getIds(Task entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
