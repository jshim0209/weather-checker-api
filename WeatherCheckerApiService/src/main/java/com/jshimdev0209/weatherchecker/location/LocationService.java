package com.jshimdev0209.weatherchecker.location;

import com.jshimdev0209.weatherchecker.common.Location;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        super();
        this.locationRepository = locationRepository;
    }

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> listLocations() {
        return locationRepository.findUntrashedLocations();
    }

    public Location getLocation(String code) {
        return locationRepository.findByCode(code);
    }

    public Location updateLocation(Location locationInRequest) throws LocationNotFoundException {
        String code = locationInRequest.getCode();

        Location locationInDB = locationRepository.findByCode(code);

        if (locationInDB == null) {
            throw new LocationNotFoundException("No location found with the given code: " + code);
        }

        locationInDB.setCityName(locationInRequest.getCityName());
        locationInDB.setRegionName(locationInRequest.getRegionName());
        locationInDB.setCountryCode(locationInRequest.getCountryCode());
        locationInDB.setCountryName(locationInRequest.getCountryName());
        locationInDB.setEnabled(locationInRequest.isEnabled());

        return locationRepository.save(locationInDB);
    }

    public void deleteLocation(String code) throws LocationNotFoundException {
        Location location = locationRepository.findByCode(code);
        if (location == null) {
            throw new LocationNotFoundException("No location found with the given code: " + code);
        }
        locationRepository.trashByCode(code);
    }
}
