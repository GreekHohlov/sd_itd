package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentsDTO extends GenericDTO{
    private String attachmentPath;
    private Long taskId;
}
