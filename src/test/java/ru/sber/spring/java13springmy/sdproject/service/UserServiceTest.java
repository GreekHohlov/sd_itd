package ru.sber.spring.java13springmy.sdproject.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.sber.spring.java13springmy.UserTestData;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.UserMapper;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest extends GenericTest<User, UserDTO> {


    public UserServiceTest() {
        super();
        repository = Mockito.mock(UserRepository.class);
        mapper = Mockito.mock(UserMapper.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        JavaMailSender javaMailSender = Mockito.mock(JavaMailSender.class);
        service = new UserService((UserRepository) repository, (UserMapper) mapper, bCryptPasswordEncoder, javaMailSender);
    }

    @Test
    @Order(1)
    @Override
    protected void listAll() {
        Mockito.when(repository.findAll()).thenReturn(UserTestData.USER_LIST);
        Mockito.when(mapper.toDTOs(UserTestData.USER_LIST)).thenReturn(UserTestData.USER_DTO_LIST);
        List<UserDTO> userDTOS = service.listAll();
        log.info("Testing getAll(): " + userDTOS);
        assertEquals(UserTestData.USER_LIST.size(), userDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserTestData.USER_1));
        Mockito.when(mapper.toDto(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO1);
        UserDTO userDTO = service.getOne(1L);
        log.info("Testing getOne(): " + userDTO);
        assertEquals(UserTestData.USER_DTO1, userDTO);
    }

    @Test
    @Order(3)
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(UserTestData.USER_DTO1)).thenReturn(UserTestData.USER_1);
        Mockito.when(mapper.toDto(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO1);
        Mockito.when(repository.save(UserTestData.USER_1)).thenReturn(UserTestData.USER_1);
        UserDTO userDTO = service.create(UserTestData.USER_DTO1);
        log.info("Testing create(): " + userDTO);
        assertEquals(UserTestData.USER_DTO1, userDTO);
    }

    @Test
    @Order(4)
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(UserTestData.USER_DTO2)).thenReturn(UserTestData.USER_2);
        Mockito.when(mapper.toDto(UserTestData.USER_2)).thenReturn(UserTestData.USER_DTO2);
        Mockito.when(repository.save(UserTestData.USER_2)).thenReturn(UserTestData.USER_2);
        UserDTO userDTO = service.update(UserTestData.USER_DTO2);
        log.info("Testing create(): " + userDTO);
        assertEquals(UserTestData.USER_DTO2, userDTO);
    }

    @Test
    @Order(5)
    @Override
    protected void delete() throws MyDeleteException {

    }

    @Test
    @Order(6)
    protected void restore() {
        UserTestData.USER_2.setDeleted(true);
        Mockito.when(repository.save(UserTestData.USER_2)).thenReturn(UserTestData.USER_2);
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(UserTestData.USER_2));
        log.info("Testing restore() before: " + UserTestData.USER_2.isDeleted());
        ((UserService) service).restore(2L);
        log.info("Testing restore() after: " + UserTestData.USER_2.isDeleted());
        assertFalse(UserTestData.USER_2.isDeleted());
    }
}
