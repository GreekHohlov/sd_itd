package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.GroupDTO;
import ru.sber.spring.java13springmy.sdproject.model.Group;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface GroupTestData {
    GroupDTO GROUP_DTO1 = new GroupDTO("nameGroup1",
            new HashSet<>());
    GroupDTO GROUP_DTO2 = new GroupDTO("nameGroup2",
            new HashSet<>());
    GroupDTO GROUP_DTO3 = new GroupDTO("nameGroup3",
            new HashSet<>());


    List<GroupDTO> GROUP_DTO_LIST = Arrays.asList(GROUP_DTO1, GROUP_DTO2, GROUP_DTO3);

    Group GROUP_1 = new Group("nameGroup1",
            new HashSet<>());
    Group GROUP_2 = new Group("nameGroup2",
            new HashSet<>());
    Group GROUP_3 = new Group("nameGroup3",
            new HashSet<>());

    List<Group> GROUP_LIST = Arrays.asList(GROUP_1, GROUP_2, GROUP_3);
}
