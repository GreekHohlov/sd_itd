package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddTaskDTO {
    Long taskId;
    Long workerId;
}
