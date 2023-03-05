package ru.sber.spring.java13springmy.sdproject.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.model.SLA;

import java.util.Set;

@Component
public class SLAMapper extends GenericMapper<SLA, SLADTO> {

    protected SLAMapper (ModelMapper modelMapper) {
        super(modelMapper,SLA.class, SLADTO.class);

    }
    @Override
    protected void mapSpecificFields(SLADTO source, SLA destination) {

    }

    @Override
    protected void mapSpecificFields(SLA source, SLADTO destination) {

    }

    @Override
    protected Set<Long> getIds(SLA entity) {
        return null;
    }
}
