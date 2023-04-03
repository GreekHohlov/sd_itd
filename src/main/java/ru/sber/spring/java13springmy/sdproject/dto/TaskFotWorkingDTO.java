package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaskFotWorkingDTO extends GenericDTO {
    private StatusTask statusTask;
    private LocalDate endDate;
    private Long workerId;
    private String decision;
}
