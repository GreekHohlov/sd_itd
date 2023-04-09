package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.SLADTO;
import ru.sber.spring.java13springmy.sdproject.exception.MyDeleteException;
import ru.sber.spring.java13springmy.sdproject.mapper.SLAMapper;
import ru.sber.spring.java13springmy.sdproject.model.SLA;
import ru.sber.spring.java13springmy.sdproject.repository.SLARepository;

@Service
public class SLAService extends GenericService<SLA, SLADTO> {
    protected SLAService(SLARepository slaRepository,
                         SLAMapper slaMapper) {
        super(slaRepository, slaMapper);
    }

    public void deleteSoft(Long id) throws MyDeleteException {
        SLA sla = repository.findById(id).orElseThrow(
                () -> new NotFoundException("SLA с заданным ID=" + id + " не существует"));
        markAsDeleted(sla);
        repository.save(sla);
    }

    public void restore(Long objectId) {
        SLA sla = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("SLA с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(sla);
        repository.save(sla);
    }
}
