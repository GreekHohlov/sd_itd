package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.GroupMapper;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;
@Service
public class GroupService  extends GenericService<Group, GroupDTO> {
    protected GroupService (GroupRepository groupRepository, GroupMapper groupMapper) {
        super(groupRepository,groupMapper);

    }
}
