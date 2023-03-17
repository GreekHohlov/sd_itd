package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentsWithTaskDTO extends TaskDTO{
    private Long taskId;
}
