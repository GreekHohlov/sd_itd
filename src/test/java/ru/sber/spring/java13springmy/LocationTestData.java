package ru.sber.spring.java13springmy;

import ru.sber.spring.java13springmy.sdproject.dto.LocationDTO;
import ru.sber.spring.java13springmy.sdproject.model.Location;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface LocationTestData {
    LocationDTO LOCATION_DTO1 = new LocationDTO("nameLocation1",
            new HashSet<>());
    LocationDTO LOCATION_DTO2 = new LocationDTO("nameLocation2",
            new HashSet<>());
    LocationDTO LOCATION_DTO3 = new LocationDTO("nameLocation3",
            new HashSet<>());

    List<LocationDTO> LOCATION_DTO_LIST = Arrays.asList(LOCATION_DTO1, LOCATION_DTO2, LOCATION_DTO3);

    Location LOCATION_1 = new Location("nameLocation1",
            new HashSet<>());
    Location LOCATION_2 = new Location("nameLocation2",
            new HashSet<>());
    Location LOCATION_3 = new Location("nameLocation3",
            new HashSet<>());

    List<Location> LOCATION_LIST = Arrays.asList(LOCATION_1, LOCATION_2, LOCATION_3);
}
