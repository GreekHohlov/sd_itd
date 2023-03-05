package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.CategoryDTO;
import ru.sber.spring.java13springmy.sdproject.model.Category;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.repository.CategoryRepository;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper extends GenericMapper<Category, CategoryDTO> {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    protected CategoryMapper(ModelMapper modelMapper, TaskRepository taskRepository,
                             CategoryRepository categoryRepository){
        super(modelMapper, Category.class, CategoryDTO.class);
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }
    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Category.class, CategoryDTO.class)
                .addMappings(m -> m.skip(CategoryDTO::setTaskIds)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(CategoryDTO::setSubCategoryIds)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(CategoryDTO::setParentCategoryId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(CategoryDTO.class, Category.class)
                .addMappings(m -> m.skip(Category::setTasks)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Category::setSubCategory)).setPostConverter(toEntityConverter());
    }
    @Override
    protected void mapSpecificFields(CategoryDTO source, Category destination) {
        if(!Objects.isNull(source.getSubCategoryIds())){
            destination.setSubCategory(new HashSet<>(categoryRepository.findAllById(source.getSubCategoryIds())));
        }
        else {
            destination.setSubCategory(Collections.emptySet());
        }
        if(!Objects.isNull(source.getTaskIds())){
            destination.setTasks(new HashSet<>(taskRepository.findAllById(source.getTaskIds())));
        }
        else {
            destination.setTasks(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(Category source, CategoryDTO destination) {
        destination.setTaskIds(getTaskIds(source));
        destination.setSubCategoryIds(getSubIds(source));
    }

    @Override
    protected Set<Long> getIds(Category entity) {
        throw new UnsupportedOperationException("Метод не используется");
    }

    protected Set<Long> getTaskIds(Category entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTasks())
                ? Collections.emptySet()
                : entity.getTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
    protected Set<Long> getSubIds(Category entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getSubCategory())
                ? Collections.emptySet()
                : entity.getSubCategory().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
