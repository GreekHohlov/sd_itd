package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO extends GenericDTO {
    private String nameLocation;
    private Set<Long> usersIds;
}
