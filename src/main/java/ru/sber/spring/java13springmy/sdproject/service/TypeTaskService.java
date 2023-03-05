package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.TypeTaskMapper;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;

@Service
public class TypeTaskService extends GenericService<TypeTask, TypeTaskDTO>{
    protected TypeTaskService(TypeTaskRepository typeTaskRepository, TypeTaskMapper typeTaskMapper){
        super(typeTaskRepository, typeTaskMapper);
    }
}
