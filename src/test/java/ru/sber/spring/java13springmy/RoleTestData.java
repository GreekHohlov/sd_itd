package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.dto.RoleDTO;
import ru.sber.spring.java13springmy.sdproject.model.Location;
import ru.sber.spring.java13springmy.sdproject.model.Role;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface RoleTestData {

    RoleDTO ROLE_DTO1 = new RoleDTO("nameRole1",
            "description1",
            new HashSet<>());
    RoleDTO ROLE_DTO2 = new RoleDTO("nameRole2",
            "description2",
            new HashSet<>());
    RoleDTO ROLE_DTO3 = new RoleDTO("nameRole3",
            "description3",
            new HashSet<>());

    List<RoleDTO> ROLE_DTO_LIST = Arrays.asList(ROLE_DTO1, ROLE_DTO2, ROLE_DTO3);

    Role ROLE_1 = new Role("nameLocation1",
            "description1");
    Role ROLE_2 = new Role("nameLocation2",
            "description2");
    Role ROLE_3 = new Role("nameLocation3",
            "description3");

    List<Role> ROLE_LIST = Arrays.asList(ROLE_1, ROLE_2, ROLE_3);
}
