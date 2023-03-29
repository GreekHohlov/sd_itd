package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.constants.MailConstants;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.UserMapper;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.utils.MailUtils;

import java.util.UUID;

@Service
public class UserService extends GenericService<User, UserDTO> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    protected UserService(UserRepository userRepository, UserMapper userMapper,
                          BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender) {
        super(userRepository, userMapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public UserDTO getUserByLogin(final String login) {
        return mapper.toDto(((UserRepository) repository).findUsersByLogin(login));
    }

    public UserDTO getUserByEmail(final String email) {
        return mapper.toDto(((UserRepository) repository).findUsersByEmail(email));
    }

    public UserDTO getWorkers(final Group groupId) {
        return mapper.toDto(((UserRepository) repository).findUsersByGroup(groupId));
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

    public void sendChangePasswordEmail(final UserDTO userDTO) {
        UUID uuid = UUID.randomUUID();
        userDTO.setChangePasswordToken(uuid.toString());
        update(userDTO);
        SimpleMailMessage mailMessage = MailUtils.createEmailMessage(userDTO.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid);
        javaMailSender.send(mailMessage);
    }

    public void changePassword(final String uuid,
                               final String password) {
        UserDTO user = mapper.toDto(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        user.setChangePasswordToken(null);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        update(user);
    }

}
