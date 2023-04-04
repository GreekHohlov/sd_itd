package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.GenericDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.GenericMapper;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.repository.GenericRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Абстрактный сервис который хранит в себе реализацию CRUD операций по умолчанию
 * Если реализация отличная от того что представлено в этом классе,
 * то она переопределяется в сервисе для конкретной сущности
 *
 * @param <T> - Сущность с которой мы работаем
 * @param <N> - DTO, которую мы будем отдавать/принимать дальше
 */
@Service
public abstract class GenericService<T extends GenericModel, N extends GenericDTO> {

    //Инжектим абстрактный репозиторий для работы с базой данных
    protected final GenericRepository<T> repository;
    protected final GenericMapper<T, N> mapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected GenericService(GenericRepository<T> repository,
                             GenericMapper<T, N> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<N> listAll() {
        return mapper.toDTOs(repository.findAll());
    }

    public Page<N> listAll(Pageable pageable) {
        Page<T> objects = repository.findAll(pageable);
        List<N> result = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }

    public N getOne(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Данных по заданному id: " + id + " не найдены")));
    }

    public N create(N object) {
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

    public N update(N object) {
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

    public void delete(Long id) throws MyDeleteException {
        repository.deleteById(id);
    }

    public void markAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(true);
        genericModel.setDeletedWhen(LocalDateTime.now());
        genericModel.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void unMarkAsDeleted(GenericModel genericModel) {
        genericModel.setDeleted(false);
        genericModel.setDeletedWhen(null);
        genericModel.setDeletedBy(null);
    }
}
