package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.TypeTaskTempDTO;
import ru.sber.spring.java13springmy.sdproject.model.GenericModel;
import ru.sber.spring.java13springmy.sdproject.model.TypeTask;
import ru.sber.spring.java13springmy.sdproject.repository.TaskRepository;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TypeTaskTempMapper extends GenericMapper<TypeTask, TypeTaskTempDTO>{
    protected TypeTaskTempMapper(ModelMapper modelMapper){
        super(modelMapper, TypeTask.class, TypeTaskTempDTO.class);
    }
    @PostConstruct
    protected void setupMapper(){
        modelMapper.createTypeMap(TypeTask.class, TypeTaskTempDTO.class)
                .addMappings(m -> m.skip(TypeTaskTempDTO::setNameType)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TypeTaskTempDTO::setSlaId)).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapSpecificFields(TypeTaskTempDTO source, TypeTask destination) {

    }

    @Override
    protected void mapSpecificFields(TypeTask source, TypeTaskTempDTO destination) {
        destination.setNameType(source.getNameType());
        destination.setSlaId(source.getSla().getId());
    }

    @Override
    protected Set<Long> getIds(TypeTask entity) {
        throw new UnsupportedOperationException("Метод не поддерживатеся");
    }
}
