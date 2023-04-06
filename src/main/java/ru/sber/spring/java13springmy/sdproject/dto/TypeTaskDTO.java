package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.*;
import ru.sber.spring.java13springmy.sdproject.model.SLA;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypeTaskDTO extends GenericDTO{
    private String nameType;
    private Long slaId;
    private SLA sla;
    private Set<Long> taskIds;
}
