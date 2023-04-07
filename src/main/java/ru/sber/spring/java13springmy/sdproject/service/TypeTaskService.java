package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.TypeTaskMapper;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;

@Service
public class TypeTaskService extends GenericService<TypeTask, TypeTaskDTO>{
    protected TypeTaskService(TypeTaskRepository typeTaskRepository,
                              TypeTaskMapper typeTaskMapper){
        super(typeTaskRepository, typeTaskMapper);
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
}
