package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CalendarDTO extends GenericDTO {
    private String nameEvent;
    private LocalDate startEvent;
    private LocalDate endEvent;
}
