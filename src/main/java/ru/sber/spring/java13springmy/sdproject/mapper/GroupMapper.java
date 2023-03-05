package ru.sber.spring.java13springmy.sdproject.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.model.Group;

import java.util.Set;
@Component
public class GroupMapper extends GenericMapper<Group, GroupDTO> {
    protected GroupMapper(ModelMapper modelMapper) {
        super(modelMapper, Group.class, GroupDTO.class);
    }

    @Override
    protected void mapSpecificFields(GroupDTO source, Group destination) {

    }

    @Override
    protected void mapSpecificFields(Group source, GroupDTO destination) {

    }

    @Override
    protected Set<Long> getIds(Group entity) {
        return null;
    }
}
