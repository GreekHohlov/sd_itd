package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryDTO extends GenericDTO{
    private String nameCategory;
    private Set<Long> taskIds;
    private Long parentCategoryId;
    private Set<Long> subCategoryIds;
}