package ru.sber.spring.java13springmy.sdproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.sber.spring.java13springmy.sdproject.model.Category;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryDTO extends GenericDTO{
    private String nameCategory;
    private Set<Long> taskIds;
    private Long parentCategoryNum;
    private Set<Long> subCategoryIds;
   private Category parentCategoryId;
}
