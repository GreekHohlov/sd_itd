package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.UserMapper;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

@Service
public class UserService extends GenericService<User, UserDTO> {
    protected UserService(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository, userMapper);
    }
}
