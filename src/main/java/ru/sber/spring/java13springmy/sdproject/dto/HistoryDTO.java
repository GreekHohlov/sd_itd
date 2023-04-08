package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.*;
import ru.sber.spring.java13springmy.sdproject.dto.GenericDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HistoryDTO extends GenericDTO {
    private Long taskId;
    private String event;
}
