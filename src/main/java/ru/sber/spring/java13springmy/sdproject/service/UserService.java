package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.constants.MailConstants;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.UserMapper;
import ru.sber.spring.java13springmy.sdproject.model.User;
import ru.sber.spring.java13springmy.sdproject.repository.UserRepository;
import ru.sber.spring.java13springmy.sdproject.utils.MailUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.sber.spring.java13springmy.sdproject.constants.UserRoleConstants.ADMIN;

@Service
public class UserService extends GenericService<User, UserDTO> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    protected UserService(UserRepository userRepository,
                          UserMapper userMapper,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          JavaMailSender javaMailSender) {
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

    @Override
    public UserDTO create(UserDTO object) {
        RoleDTO roleDTO = new RoleDTO();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (ADMIN.equalsIgnoreCase(userName)) {
            roleDTO.setId(2L);//исполнитель
            object.setWorker(true);
        } else {
            roleDTO.setId(1L);//пользователь
        }
        object.setRole(roleDTO);
        object.setCreatedBy("REGISTRATION FORM");
        object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        if (object.getGroupId() == null) {
            object.setGroupId(1);
        }
        if (object.getLocationId() == null) {
            object.setLocationId(1);
        }
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

    public Page<UserDTO> findUsers(UserDTO userDTO,
                                   Pageable pageable) {
        Page<User> users = ((UserRepository) repository).searchUsers(userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getLogin(),
                pageable);
        List<UserDTO> result = mapper.toDTOs(users.getContent());
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }

    @Override
    public void delete(Long id) throws MyDeleteException {
        User user = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователя с заданным ID=" + id + " не существует"));
        markAsDeleted(user);
        repository.save(user);
    }

    public void restore(Long objectId) {
        User user = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Пользователя с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(user);
        repository.save(user);
    }
}
