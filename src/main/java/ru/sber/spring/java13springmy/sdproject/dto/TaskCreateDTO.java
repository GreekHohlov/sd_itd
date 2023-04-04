package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskCreateDTO extends GenericDTO{
    private Long userId;
    private Long workerId;
    private Long categoryId;
}
