package com.jshimdev0209.weatherchecker.location;

import com.jshimdev0209.weatherchecker.common.Location;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        super();
        this.locationRepository = locationRepository;
    }

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }
}
