package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.mapper.SLAMapper;
import ru.sber.spring.java13springmy.sdproject.model.SLA;
import ru.sber.spring.java13springmy.sdproject.repository.SLARepository;

@Service
public class SLAService extends  GenericService <SLA, SLADTO> {
    protected SLAService (SLARepository slaRepository, SLAMapper slaMapper) {
        super(slaRepository,slaMapper);
    }
}
