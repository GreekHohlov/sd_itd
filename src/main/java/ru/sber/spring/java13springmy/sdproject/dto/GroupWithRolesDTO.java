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
public class GroupWithRolesDTO extends GroupDTO {
    private Set<RoleDTO>  roles;
}
