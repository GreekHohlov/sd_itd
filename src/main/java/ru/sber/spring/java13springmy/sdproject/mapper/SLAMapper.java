package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.SLA;
import ru.sber.spring.java13springmy.sdproject.repository.TypeTaskRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SLAMapper extends GenericMapper<SLA, SLADTO> {
    private final TypeTaskRepository typeTaskRepository;

    protected SLAMapper(ModelMapper modelMapper, TypeTaskRepository typeTaskRepository) {
        super(modelMapper, SLA.class, SLADTO.class);
        this.typeTaskRepository = typeTaskRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(SLA.class, SLADTO.class)
                .addMappings(m -> m.skip(SLADTO::setTypeTasksIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(SLADTO.class, SLA.class)
                .addMappings(m -> m.skip(SLA::setTypeTask)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(SLADTO source, SLA destination) {
        if (!Objects.isNull(source.getTypeTasksIds())) {
            destination.setTypeTask(new HashSet<>(typeTaskRepository.findAllById(source.getTypeTasksIds())));
        } else {
            destination.setTypeTask(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(SLA source, SLADTO destination) {
        destination.setTypeTasksIds(getIds(source));
    }

    @Override
    protected Set<Long> getIds(SLA entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTypeTask())
                ? null
                : entity.getTypeTask().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}
