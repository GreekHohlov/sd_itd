package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.GroupMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.RoleMapper;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;
import ru.sber.spring.java13springmy.sdproject.repository.RoleRepository;

import java.util.List;

@Service
public class GroupService extends GenericService<Group, GroupDTO> {
    private final GroupMapper groupMapper;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    protected GroupService(GroupRepository groupRepository,
                           GroupMapper groupMapper, RoleMapper roleMapper, RoleRepository roleRepository) {
        super(groupRepository, groupMapper);
        this.groupMapper = groupMapper;
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }


    public void deleteSoft(Long id) throws MyDeleteException {
        Group group = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Группы с заданным ID=" + id + " не существует"));
        markAsDeleted(group);
        repository.save(group);
    }

    public void restore(Long objectId) {
        Group group = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Группы с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(group);
        repository.save(group);
    }
}
