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
public class TypeTaskDTO extends GenericDTO{
    private String nameType;
    private Long slaId;
    private Set<Long> taskIds;
}
