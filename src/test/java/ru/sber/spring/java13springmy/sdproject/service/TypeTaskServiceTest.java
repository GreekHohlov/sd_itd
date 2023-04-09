package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.TypeTaskTestData;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.TypeTaskMapper;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TypeTaskServiceTest extends GenericTest<TypeTask, TypeTaskDTO> {

    public TypeTaskServiceTest() {
        super();
        repository = Mockito.mock(TypeTaskRepository.class);
        mapper = Mockito.mock(TypeTaskMapper.class);
        service = new TypeTaskService((TypeTaskRepository) repository, (TypeTaskMapper) mapper);
    }

    @Test
    @Order(1)
    @Override
    protected void listAll() {
        Mockito.when(repository.findAll()).thenReturn(TypeTaskTestData.TYPE_TASK_LIST);
        Mockito.when(mapper.toDTOs(TypeTaskTestData.TYPE_TASK_LIST)).thenReturn(TypeTaskTestData.TASK_DTO_LIST);
        List<TypeTaskDTO> taskDTOS = service.listAll();
        log.info("Testing getAll(): " + taskDTOS);
        assertEquals(TypeTaskTestData.TYPE_TASK_LIST.size(), taskDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TypeTaskTestData.TYPE_TASK_1));
        Mockito.when(mapper.toDto(TypeTaskTestData.TYPE_TASK_1)).thenReturn(TypeTaskTestData.TYPE_TASK_DTO1);
        TypeTaskDTO typeTaskDTO = service.getOne(1L);
        log.info("Testing getOne(): " + typeTaskDTO);
        assertEquals(TypeTaskTestData.TYPE_TASK_DTO1, typeTaskDTO);
    }

    @Test
    @Order(3)
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(TypeTaskTestData.TYPE_TASK_DTO1)).thenReturn(TypeTaskTestData.TYPE_TASK_1);
        Mockito.when(mapper.toDto(TypeTaskTestData.TYPE_TASK_1)).thenReturn(TypeTaskTestData.TYPE_TASK_DTO1);
        Mockito.when(repository.save(TypeTaskTestData.TYPE_TASK_1)).thenReturn(TypeTaskTestData.TYPE_TASK_1);
        TypeTaskDTO typeTaskDTO = service.create(TypeTaskTestData.TYPE_TASK_DTO1);
        log.info("Testing create(): " + typeTaskDTO);
        assertEquals(TypeTaskTestData.TYPE_TASK_DTO1, typeTaskDTO);
    }

    @Test
    @Order(4)
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(TypeTaskTestData.TYPE_TASK_DTO2)).thenReturn(TypeTaskTestData.TYPE_TASK_2);
        Mockito.when(mapper.toDto(TypeTaskTestData.TYPE_TASK_2)).thenReturn(TypeTaskTestData.TYPE_TASK_DTO2);
        Mockito.when(repository.save(TypeTaskTestData.TYPE_TASK_2)).thenReturn(TypeTaskTestData.TYPE_TASK_2);
        TypeTaskDTO typeTaskDTO = service.update(TypeTaskTestData.TYPE_TASK_DTO2);
        log.info("Testing create(): " + typeTaskDTO);
        assertEquals(TypeTaskTestData.TYPE_TASK_DTO2, typeTaskDTO);
    }

    @Test
    @Order(5)
    @Override
    protected void delete() throws MyDeleteException {

    }

    @Test
    @Order(6)
    protected void restore() {
        TypeTaskTestData.TYPE_TASK_2.setDeleted(true);
        Mockito.when(repository.save(TypeTaskTestData.TYPE_TASK_2)).thenReturn(TypeTaskTestData.TYPE_TASK_2);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(TypeTaskTestData.TYPE_TASK_2));
        log.info("Testing restore() before: " + TypeTaskTestData.TYPE_TASK_2.isDeleted());
        ((TypeTaskService) service).restore(2L);
        log.info("Testing restore() after: " + TypeTaskTestData.TYPE_TASK_2.isDeleted());
        assertFalse(TypeTaskTestData.TYPE_TASK_2.isDeleted());
    }
}
