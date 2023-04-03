package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.CalendarDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.CalendarMapper;
import ru.sber.spring.java13springmy.sdproject.model.Calendar;
import ru.sber.spring.java13springmy.sdproject.repository.CalendarRepository;

@Service
public class CalendarService  extends GenericService<Calendar, CalendarDTO> {
    /* Так тоже должно работать
    protected CalendarService(GenericRepository<Calendar> repository, GenericMapper<Calendar, CalendarDTO> mapper) {
        super(repository, mapper);
    }
     */

    protected CalendarService (CalendarRepository calendarRepository, CalendarMapper calendarMapper) {
        super(calendarRepository,calendarMapper);
    }
}
