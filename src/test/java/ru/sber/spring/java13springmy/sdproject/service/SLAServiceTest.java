package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.SLATestData;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.SLAMapper;
import ru.sber.spring.java13springmy.sdproject.model.SLA;
import ru.sber.spring.java13springmy.sdproject.repository.SLARepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SLAServiceTest extends GenericTest<SLA, SLADTO> {
    public SLAServiceTest() {
        super();
        repository = Mockito.mock(SLARepository.class);
        mapper = Mockito.mock(SLAMapper.class);
        service = new SLAService((SLARepository) repository, (SLAMapper) mapper);
    }

    @Test
    @Order(1)
    @Override
    protected void listAll() {
        Mockito.when(repository.findAll()).thenReturn(SLATestData.SLA_LIST);
        Mockito.when(mapper.toDTOs(SLATestData.SLA_LIST)).thenReturn(SLATestData.SLADTO_LIST);
        List<SLADTO> sladtos = service.listAll();
        log.info("Testing getAll(): " + sladtos);
        assertEquals(SLATestData.SLA_LIST.size(), sladtos.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(SLATestData.SLA_1));
        Mockito.when(mapper.toDto(SLATestData.SLA_1)).thenReturn(SLATestData.SLA_DTO_1);
        SLADTO sladto = service.getOne(1L);
        log.info("Testing getOne(): " + sladto);
        assertEquals(SLATestData.SLA_DTO_1, sladto);
    }

    @Test
    @Order(3)
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(SLATestData.SLA_DTO_1)).thenReturn(SLATestData.SLA_1);
        Mockito.when(mapper.toDto(SLATestData.SLA_1)).thenReturn(SLATestData.SLA_DTO_1);
        Mockito.when(repository.save(SLATestData.SLA_1)).thenReturn(SLATestData.SLA_1);
        SLADTO sladto = service.create(SLATestData.SLA_DTO_1);
        log.info("Testing create(): " + sladto);
        assertEquals(SLATestData.SLA_DTO_1, sladto);
    }

    @Test
    @Order(4)
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(SLATestData.SLA_DTO_2)).thenReturn(SLATestData.SLA_2);
        Mockito.when(mapper.toDto(SLATestData.SLA_2)).thenReturn(SLATestData.SLA_DTO_2);
        Mockito.when(repository.save(SLATestData.SLA_2)).thenReturn(SLATestData.SLA_2);
        SLADTO sladto = service.update(SLATestData.SLA_DTO_2);
        log.info("Testing create(): " + sladto);
        assertEquals(SLATestData.SLA_DTO_2, sladto);
    }

    @Test
    @Order(5)
    @Override
    protected void delete() throws MyDeleteException {

    }

    @Test
    @Order(6)
    protected void restore() {
        SLATestData.SLA_2.setDeleted(true);
        Mockito.when(repository.save(SLATestData.SLA_2)).thenReturn(SLATestData.SLA_2);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(SLATestData.SLA_2));
        log.info("Testing restore() before: " + SLATestData.SLA_2.isDeleted());
        ((SLAService) service).restore(2L);
        log.info("Testing restore() after: " + SLATestData.SLA_2.isDeleted());
        assertFalse(SLATestData.SLA_2.isDeleted());
    }
}
