package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.LocationTestData;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.LocationMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.RoleMapper;
import ru.sber.spring.java13springmy.sdproject.model.Role;
import ru.sber.spring.java13springmy.sdproject.repository.LocationRepository;
import ru.sber.spring.java13springmy.sdproject.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleServiceTest extends GenericTest<Role, RoleDTO> {

    public RoleServiceTest() {
        super();
        repository = Mockito.mock(RoleRepository.class);
        mapper = Mockito.mock(RoleMapper.class);
        service = new RoleService((RoleRepository) repository, (RoleMapper) mapper);
    }
    @Test
    @Order(1)
    @Override
    protected void listAll() {
//        Mockito.when(repository.findAll()).thenReturn(LocationTestData.LOCATION_LIST);
//        Mockito.when(mapper.toDTOs(LocationTestData.LOCATION_LIST)).thenReturn(LocationTestData.LOCATION_DTO_LIST);
//        List<LocationDTO> locationDTOS = service.listAll();
//        log.info("Testing getAll(): " + locationDTOS);
//        assertEquals(LocationTestData.LOCATION_LIST.size(), locationDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
//        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(LocationTestData.LOCATION_1));
//        Mockito.when(mapper.toDto(LocationTestData.LOCATION_1)).thenReturn(LocationTestData.LOCATION_DTO1);
//        LocationDTO locationDTO = service.getOne(1L);
//        log.info("Testing getOne(): " + locationDTO);
//        assertEquals(LocationTestData.LOCATION_DTO1, locationDTO);
    }

    @Test
    @Order(3)
    @Override
    protected void create() {
//        Mockito.when(mapper.toEntity(LocationTestData.LOCATION_DTO1)).thenReturn(LocationTestData.LOCATION_1);
//        Mockito.when(mapper.toDto(LocationTestData.LOCATION_1)).thenReturn(LocationTestData.LOCATION_DTO1);
//        Mockito.when(repository.save(LocationTestData.LOCATION_1)).thenReturn(LocationTestData.LOCATION_1);
//        LocationDTO locationDTO = service.create(LocationTestData.LOCATION_DTO1);
//        log.info("Testing create(): " + locationDTO);
//        assertEquals(LocationTestData.LOCATION_DTO1, locationDTO);
    }

    @Test
    @Order(4)
    @Override
    protected void update() {
//        Mockito.when(mapper.toEntity(LocationTestData.LOCATION_DTO2)).thenReturn(LocationTestData.LOCATION_2);
//        Mockito.when(mapper.toDto(LocationTestData.LOCATION_2)).thenReturn(LocationTestData.LOCATION_DTO2);
//        Mockito.when(repository.save(LocationTestData.LOCATION_2)).thenReturn(LocationTestData.LOCATION_2);
//        LocationDTO locationDTO = service.update(LocationTestData.LOCATION_DTO2);
//        log.info("Testing create(): " + locationDTO);
//        assertEquals(LocationTestData.LOCATION_DTO2, locationDTO);
    }

    @Test
    @Order(5)
    @Override
    protected void delete() throws MyDeleteException {

    }

    @Test
    @Order(6)
    protected void restore() {
//        LocationTestData.LOCATION_2.setDeleted(true);
//        Mockito.when(repository.save(LocationTestData.LOCATION_2)).thenReturn(LocationTestData.LOCATION_2);
//        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(LocationTestData.LOCATION_2));
//        log.info("Testing restore() before: " + LocationTestData.LOCATION_2.isDeleted());
//        ((LocationService) service).restore(2L);
//        log.info("Testing restore() after: " + LocationTestData.LOCATION_2.isDeleted());
//        assertFalse(LocationTestData.LOCATION_2.isDeleted());
    }
}
