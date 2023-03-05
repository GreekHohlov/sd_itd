package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class GroupDTO extends GenericDTO {
    private String responsible;
    private Integer role;
    private Set<Long> usersIds;
}
