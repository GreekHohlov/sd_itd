package ru.sber.spring.java13springmy.sdproject.service;

import org.springframework.stereotype.Service;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.mapper.LocationMapper;
import ru.sber.spring.java13springmy.sdproject.model.Location;
import ru.sber.spring.java13springmy.sdproject.repository.LocationRepository;

@Service
public class LocationService extends GenericService<Location, LocationDTO> {
    protected LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        super(locationRepository, locationMapper);
    }
}
