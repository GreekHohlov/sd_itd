package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.GroupMapper;
import ru.sber.spring.java13springmy.sdproject.mapper.RoleMapper;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.repository.GroupRepository;
import ru.sber.spring.java13springmy.sdproject.repository.RoleRepository;

import java.util.List;

@Service
public class GroupService  extends GenericService<Group, GroupDTO> {
   private final GroupMapper groupMapper;
   private final RoleMapper roleMapper;
   private  final RoleRepository roleRepository;
    protected GroupService (GroupRepository groupRepository,
                            GroupMapper groupMapper, RoleMapper roleMapper, RoleRepository roleRepository) {
        super(groupRepository,groupMapper);

       this.groupMapper = groupMapper;
        this.roleMapper = roleMapper;

        this.roleRepository = roleRepository;
    }

//    public List<TaskWithUserDTO> getAllTaskWithUser() {
//        return taskWithUserMapper.toDTOs(taskRepository.findAll());
//    }

    public List<RoleDTO> getAllRoles() {
        return roleMapper.toDTOs(roleRepository.findAll());
    }
//    public GroupWithRolesDTO getGroupWithRole (Integer id) {
//        return (GroupWithRolesDTO) groupMapper.toDto(mapper.toEntity(super.getOne(Long.valueOf(id))));
//    }

}
