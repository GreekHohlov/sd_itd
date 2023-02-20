package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class GenericDTO {
    private Long id;
    //  private boolean isDeleted;
    //  private LocalDateTime deletedWhen;
    //  private String deletedBy;
}
