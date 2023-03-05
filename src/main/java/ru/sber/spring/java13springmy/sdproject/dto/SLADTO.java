package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SLADTO extends GenericDTO {
    private String nameSLA;
    private Integer responseTime;
    private Integer executionTime;
    private Set<Long> typeTasksIds;
}
