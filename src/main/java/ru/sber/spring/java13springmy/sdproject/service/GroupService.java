package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.GroupMapper;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;

@Service
public class GroupService extends GenericService<Group, GroupDTO> {

    protected GroupService(GroupRepository groupRepository,
                           GroupMapper groupMapper) {
        super(groupRepository, groupMapper);
    }

    public void deleteSoft(Long id) {
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
