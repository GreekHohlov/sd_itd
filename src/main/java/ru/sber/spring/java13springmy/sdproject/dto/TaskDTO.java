package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.sber.spring.java13springmy.sdproject.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaskDTO extends GenericDTO{
    private String nameTask;
    private Long typeTaskId;
    private Priority priority;
    private Long categoryId;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime endDate;
    private Set<Long> attachmentsIds;
    private Long userId;
    private Long workerId;
    private StatusTask statusTask;
    private String files;
    private String decision;
}
