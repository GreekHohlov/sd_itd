package ru.sber.spring.java13springmy.sdproject.mapper;

import ru.sber.spring.java13springmy.sdproject.dto.GenericDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;

import java.util.List;

public interface Mapper<E extends GenericModel, D extends GenericDTO> {
    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

    D toDto(E entity);

    List<D> toDTOs(List<E> entities);
}
