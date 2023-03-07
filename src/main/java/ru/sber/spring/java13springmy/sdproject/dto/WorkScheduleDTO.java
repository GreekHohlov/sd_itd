package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class WorkScheduleDTO extends GenericDTO{
    private Integer year;
    private Integer month;
    private String dayOfWeek;
    private LocalDate startEvent;
    private LocalDate endEvent;
    private Boolean dayOff;
}
