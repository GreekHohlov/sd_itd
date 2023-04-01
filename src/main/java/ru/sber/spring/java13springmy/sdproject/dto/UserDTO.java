package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.sber.spring.java13springmy.sdproject.model.Group;
import ru.sber.spring.java13springmy.sdproject.model.Location;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO extends GenericDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String phone;
    private Integer locationId;
    private Integer groupId;
    private RoleDTO role;
    private String changePasswordToken;
    private Boolean worker;
    private Set<Long> tasksIds;
    private Set<Long> tasksWorkerIds;

    private Group group;
//    private GroupDTO groupDTO;
    private Location location;
    private boolean isDeleted;
}
