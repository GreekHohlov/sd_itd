package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sber.spring.java13springmy.sdproject.model.StatusTask;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskWithUserDTO extends TaskDTO{
    private UserDTO user;
    private UserDTO worker;
    private StatusTask statusTask;
    private TypeTaskDTO typeTask;
    private CategoryDTO category;
}
