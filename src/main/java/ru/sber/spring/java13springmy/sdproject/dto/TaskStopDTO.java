package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TaskStopDTO extends GenericDTO {
    private String decision;
}
