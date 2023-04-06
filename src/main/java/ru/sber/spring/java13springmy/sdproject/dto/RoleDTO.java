package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO extends GenericDTO {
    private String nameRole;
    private String description;
    private Set<Long> groupsIds;
}
