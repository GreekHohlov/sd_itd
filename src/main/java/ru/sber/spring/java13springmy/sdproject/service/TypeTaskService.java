package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskTempDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.TypeTaskMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.TypeTaskTempMapper;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;

@Service
@Slf4j
public class TypeTaskService extends GenericService<TypeTask, TypeTaskDTO>{
    private final TypeTaskRepository typeTaskRepository;
    private final TypeTaskTempMapper typeTaskTempMapper;
    protected TypeTaskService(TypeTaskRepository typeTaskRepository,
                              TypeTaskMapper typeTaskMapper,
                              TypeTaskTempMapper typeTaskTempMapper){
        super(typeTaskRepository, typeTaskMapper);
        this.typeTaskRepository = typeTaskRepository;
        this.typeTaskTempMapper = typeTaskTempMapper;
    }

    public void deleteSoft(Long id) {
        TypeTask typeTask = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Тип заявок с заданным ID=" + id + " не существует"));
        markAsDeleted(typeTask);
        repository.save(typeTask);
    }

    public void restore(Long objectId) {
        TypeTask typeTask = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Тип заявок с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(typeTask);
        repository.save(typeTask);
    }

    public TypeTaskTempDTO findByName(String nameType) {
        return typeTaskTempMapper.toDto(typeTaskRepository.findByNameType(nameType));
    }
}
