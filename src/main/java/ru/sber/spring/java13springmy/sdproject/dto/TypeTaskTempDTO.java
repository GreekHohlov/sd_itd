package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypeTaskTempDTO extends GenericDTO{
    private String nameType;
    private Long slaId;
}
