package ru.sber.spring.java13springmy.sdproject.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.model.User;

import java.util.Set;

@Component
public class UserMapper  extends GenericMapper <User, UserDTO> {

    protected  UserMapper (ModelMapper modelMapper) {
        super(modelMapper,User.class, UserDTO.class);

    }
    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {

    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {

    }

    @Override
    protected Set<Long> getIds(User entity) {
        return null;
    }
}
