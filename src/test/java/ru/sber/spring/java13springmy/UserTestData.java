package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.UserDTO;
import ru.sber.spring.java13springmy.sdproject.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface UserTestData {

    UserDTO USER_DTO1 = new UserDTO("firstName1",
            "middleName1",
            "lastName1",
            "login1",
            "password1",
            "email1",
            "phone1",
            1,
            1,
            RoleTestData.ROLE_DTO1,
            "changePasswordToken1",
            false,
            new HashSet<>(),
            new HashSet<>(),
            GroupTestData.GROUP_1,
            LocationTestData.LOCATION_1,
            false);

    UserDTO USER_DTO2 = new UserDTO("firstName2",
            "middleName2",
            "lastName2",
            "login2",
            "password2",
            "email2",
            "phone2",
            2,
            2,
            RoleTestData.ROLE_DTO2,
            "changePasswordToken2",
            false,
            new HashSet<>(),
            new HashSet<>(),
            GroupTestData.GROUP_2,
            LocationTestData.LOCATION_2,
            false);
    UserDTO USER_DTO3 = new UserDTO("firstName3",
            "middleName3",
            "lastName3",
            "login3",
            "password3",
            "email3",
            "phone3",
            3,
            3,
            RoleTestData.ROLE_DTO3,
            "changePasswordToken3",
            false,
            new HashSet<>(),
            new HashSet<>(),
            GroupTestData.GROUP_3,
            LocationTestData.LOCATION_3,
            false);

    List<UserDTO> USER_DTO_LIST = Arrays.asList(USER_DTO1, USER_DTO2, USER_DTO3);

    User USER_1 = new User("firstName1",
            "middleName1",
            "lastName1",
            "login1",
            "password1",
            "email1",
            "phone1",
            "changePasswordToken1",
            LocationTestData.LOCATION_1,
            GroupTestData.GROUP_1,
            new HashSet<>(),
            new HashSet<>(),
            false,
            RoleTestData.ROLE_1);
    User USER_2 = new User("firstName2",
            "middleName2",
            "lastName2",
            "login2",
            "password2",
            "email2",
            "phone2",
            "changePasswordToken2",
            LocationTestData.LOCATION_2,
            GroupTestData.GROUP_2,
            new HashSet<>(),
            new HashSet<>(),
            false,
            RoleTestData.ROLE_2);

    User USER_3 = new User("firstName3",
            "middleName3",
            "lastName3",
            "login3",
            "password3",
            "email3",
            "phone3",
            "changePasswordToken3",
            LocationTestData.LOCATION_3,
            GroupTestData.GROUP_3,
            new HashSet<>(),
            new HashSet<>(),
            false,
            RoleTestData.ROLE_3);
    List<User> USER_LIST = Arrays.asList(USER_1, USER_2, USER_3);
}
