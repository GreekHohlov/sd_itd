package ru.sber.spring.java13springmy.sdproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.spring.java13springmy.sdproject.dto.GenericDTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.service.GenericService;

import java.util.List;

/**
 * Абстрактный контроллер
 * который реализует все EndPoint`ы для crud операций используя абстрактный репозиторий
 *
 * @param <T> - Сущность с которой работает контроллер
 * @param <N> - DTO с которой работает контроллер
 */
@RestController
public abstract class GenericController<T extends GenericModel, N extends GenericDTO> {

    private GenericService<T, N> service;

    //  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GenericController(GenericService<T, N> service) {
        this.service = service;
    }

    @Operation(description = "Получить запись по ID", method = "getOneById")
    @RequestMapping(value = "/getOneById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<N> getOneById(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getOne(id));
    }

    @Operation(description = "Получить все записи", method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<N>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
    }

    @Operation(description = "Создать новую запись", method = "create")
    @RequestMapping(value = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<N> create(@RequestBody N newEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(newEntity));
    }

    @Operation(description = "Обновить запись", method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<N> update(@RequestBody N updatedEntity,
                                    @RequestParam(value = "id") Long id) {
        updatedEntity.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(updatedEntity));
    }

    @Operation(description = "Удалить запись по ID", method = "delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) throws MyDeleteException {
        service.delete(id);
    }
}

