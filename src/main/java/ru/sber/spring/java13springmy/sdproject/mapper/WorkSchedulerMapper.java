package ru.sber.spring.java13springmy.sdproject.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.WorkScheduleDTO;
import ru.sber.spring.java13springmy.sdproject.model.WorkSchedule;

import java.util.Set;

@Component
public class WorkSchedulerMapper extends GenericMapper <WorkSchedule, WorkScheduleDTO> {
    protected WorkSchedulerMapper(ModelMapper mapper) {
        super(mapper, WorkSchedule.class, WorkScheduleDTO.class);
    }

    @Override
    protected void mapSpecificFields(WorkScheduleDTO source, WorkSchedule destination) {
        throw new UnsupportedOperationException("Метод недоступен");
    }

    @Override
    protected void mapSpecificFields(WorkSchedule source, WorkScheduleDTO destination) {
        throw new UnsupportedOperationException("Метод недоступен");
    }

    @Override
    protected Set<Long> getIds(WorkSchedule entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
