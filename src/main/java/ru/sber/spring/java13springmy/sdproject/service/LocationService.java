package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.LocationMapper;
import ru.sber.spring.java13springmy.sdproject.model.Location;
import ru.sber.spring.java13springmy.sdproject.repository.LocationRepository;

@Service
public class LocationService extends GenericService<Location, LocationDTO> {
    protected LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        super(locationRepository, locationMapper);
    }

    public void deleteSoft(Long id) {
        Location location = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Площадки с заданным ID=" + id + " не существует"));
        markAsDeleted(location);
        repository.save(location);
    }

    public void restore(Long objectId) {
        Location location = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Площадки с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(location);
        repository.save(location);
    }
}
