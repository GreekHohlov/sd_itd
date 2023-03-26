package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.dto.GroupWithRolesDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.GroupMapper;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;
@Service
public class GroupService  extends GenericService<Group, GroupDTO> {
   // private final GroupMapper groupMapper;
    protected GroupService (GroupRepository groupRepository,
                            GroupMapper groupMapper) {
        super(groupRepository,groupMapper);

      //  this.groupMapper = groupMapper;
    }
//    public GroupWithRolesDTO getGroupWithRole (Integer id) {
//        return (GroupWithRolesDTO) groupMapper.toDto(mapper.toEntity(super.getOne(Long.valueOf(id))));
//    }

}
