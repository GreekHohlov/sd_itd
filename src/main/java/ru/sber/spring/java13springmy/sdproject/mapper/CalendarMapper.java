package ru.sber.spring.java13springmy.sdproject.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.CalendarDTO;
import ru.sber.spring.java13springmy.sdproject.model.Calendar;

import java.util.Set;

@Component
public class CalendarMapper extends GenericMapper<Calendar, CalendarDTO> {

    protected CalendarMapper(ModelMapper modelMapper) {
        super(modelMapper, Calendar.class, CalendarDTO.class);
    }

/* Возможно не понадобится
    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Calendar.class, CalendarDTO.class);
        modelMapper.createTypeMap(CalendarDTO.class, Calendar.class);
    }

*/

    @Override
    protected void mapSpecificFields(CalendarDTO source, Calendar destination) {
        throw new UnsupportedOperationException("Метод недоступен");
    }

    @Override
    protected void mapSpecificFields(Calendar source, CalendarDTO destination) {
        throw new UnsupportedOperationException("Метод недоступен");
    }

    @Override
    protected Set<Long> getIds(Calendar entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
