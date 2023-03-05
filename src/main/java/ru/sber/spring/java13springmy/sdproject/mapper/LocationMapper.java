package ru.sber.spring.java13springmy.sdproject.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.model.Location;

import java.util.Set;

@Component
public class LocationMapper extends GenericMapper<Location, LocationDTO> {

    protected LocationMapper(ModelMapper modelMapper) {
        super(modelMapper, Location.class, LocationDTO.class);
    }


    @Override
    protected void mapSpecificFields(LocationDTO source, Location destination) {

    }

    @Override
    protected void mapSpecificFields(Location source, LocationDTO destination) {

    }

    @Override
    protected Set<Long> getIds(Location entity) {
        return null;
    }
}
