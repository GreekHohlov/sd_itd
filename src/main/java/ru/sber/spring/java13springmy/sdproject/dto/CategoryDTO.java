package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.*;
import ru.sber.spring.java13springmy.sdproject.model.Category;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO extends GenericDTO {
    private String nameCategory;
    private Long parentCategoryNum;
    private Category parentCategoryId;
    private Set<Long> subCategoryIds;
    private Set<Long> taskIds;
}
