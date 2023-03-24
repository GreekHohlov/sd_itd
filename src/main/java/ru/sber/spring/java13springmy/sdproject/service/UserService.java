package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.UserMapper;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;

@Service
public class UserService extends GenericService<User, UserDTO> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    protected UserService(UserRepository userRepository, UserMapper userMapper,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(userRepository, userMapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDTO getUserByLogin(final String login) {
        return mapper.toDto(((UserRepository) repository).findUsersByLogin(login));
    }
    public UserDTO getUserByEmail(final String email) {
        return mapper.toDto(((UserRepository) repository).findUsersByEmail(email));
    }
    public UserDTO create(UserDTO object) {
        if (object.getGroupId() == null) {
            object.setGroupId(1);
        }
        if (object.getLocationId() == null) {
            object.setLocationId(1);
        }
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }
}
