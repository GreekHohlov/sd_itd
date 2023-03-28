package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.*;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskWithUserDTO extends TaskDTO{
    private UserDTO user;
    private UserDTO worker;
    private StatusTask statusTask;
    private TypeTaskDTO typeTask;
    private CategoryDTO category;
}
