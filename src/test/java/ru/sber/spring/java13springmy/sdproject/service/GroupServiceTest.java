package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import ru.sber.spring.java13springmy.GroupTestData;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.GroupMapper;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupServiceTest extends GenericTest<Group, GroupDTO> {
    public GroupServiceTest() {
        super();
        repository = Mockito.mock(GroupRepository.class);
        mapper = Mockito.mock(GroupMapper.class);
        service = new GroupService((GroupRepository) repository, (GroupMapper) mapper);
    }

    @Test
    @Order(1)
    @Override
    protected void listAll() {
        Mockito.when(repository.findAll()).thenReturn(GroupTestData.GROUP_LIST);
        Mockito.when(mapper.toDTOs(GroupTestData.GROUP_LIST)).thenReturn(GroupTestData.GROUP_DTO_LIST);
        List<GroupDTO> groupDTOS = service.listAll();
        log.info("Testing getAll(): " + groupDTOS);
        assertEquals(GroupTestData.GROUP_LIST.size(), groupDTOS.size());
    }
    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(GroupTestData.GROUP_1));
        Mockito.when(mapper.toDto(GroupTestData.GROUP_1)).thenReturn(GroupTestData.GROUP_DTO1);
        GroupDTO groupDTO = service.getOne(1L);
        log.info("Testing getOne(): " + groupDTO);
        assertEquals(GroupTestData.GROUP_DTO1, groupDTO);
    }
    @Test
    @Order(3)
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(GroupTestData.GROUP_DTO1)).thenReturn(GroupTestData.GROUP_1);
        Mockito.when(mapper.toDto(GroupTestData.GROUP_1)).thenReturn(GroupTestData.GROUP_DTO1);
        Mockito.when(repository.save(GroupTestData.GROUP_1)).thenReturn(GroupTestData.GROUP_1);
        GroupDTO groupDTO = service.create(GroupTestData.GROUP_DTO1);
        log.info("Testing create(): " + groupDTO);
        assertEquals(GroupTestData.GROUP_DTO1, groupDTO);
    }
    @Test
    @Order(4)
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(GroupTestData.GROUP_DTO2)).thenReturn(GroupTestData.GROUP_2);
        Mockito.when(mapper.toDto(GroupTestData.GROUP_2)).thenReturn(GroupTestData.GROUP_DTO2);
        Mockito.when(repository.save(GroupTestData.GROUP_2)).thenReturn(GroupTestData.GROUP_2);
        GroupDTO groupDTO = service.update(GroupTestData.GROUP_DTO2);
        log.info("Testing create(): " + groupDTO);
        assertEquals(GroupTestData.GROUP_DTO2, groupDTO);
    }
    @Test
    @Order(5)
    @Override
    protected void delete() throws MyDeleteException {

    }

    @Test
    @Order(6)
    protected void restore() {
        GroupTestData.GROUP_2.setDeleted(true);
        Mockito.when(repository.save(GroupTestData.GROUP_2)).thenReturn(GroupTestData.GROUP_2);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(GroupTestData.GROUP_2));
        log.info("Testing restore() before: " + GroupTestData.GROUP_2.isDeleted());
        ((GroupService) service).restore(2L);
        log.info("Testing restore() after: " + GroupTestData.GROUP_2.isDeleted());
        assertFalse(GroupTestData.GROUP_2.isDeleted());
    }
}
