package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskCreateDTO extends TaskDTO{
    private UserDTO worker;
    private TypeTaskDTO typeTask;
    private CategoryDTO category;
}
