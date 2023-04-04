package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.WorkScheduleDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.WorkSchedulerMapper;
import ru.sber.spring.java13springmy.sdproject.model.WorkSchedule;
import ru.sber.spring.java13springmy.sdproject.repository.WorkScheduleRepository;

@Service
public class WorkScheduleService extends GenericService <WorkSchedule, WorkScheduleDTO> {
    protected WorkScheduleService(WorkScheduleRepository workScheduleRepository, WorkSchedulerMapper workSchedulerMapper) {
        super(workScheduleRepository, workSchedulerMapper);
    }
}
