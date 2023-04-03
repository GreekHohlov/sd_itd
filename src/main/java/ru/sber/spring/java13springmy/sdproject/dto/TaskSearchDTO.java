package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;

@Getter
@Setter
@ToString
public class TaskSearchDTO extends GenericDTO{
    private Long taskId;
    private String nameTask;
    private String userFio;
    private String workerFio;
    private String category;
    private StatusTask statusTask;
}
