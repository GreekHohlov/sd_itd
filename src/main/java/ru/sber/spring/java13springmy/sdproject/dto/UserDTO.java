package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private Integer locationId;
    private Integer groupId;
    private String changePasswordToken;
    private Boolean worker;
    private Set<Long> tasksIds;
    private Set<Long> tasksWorkerIds;
}
