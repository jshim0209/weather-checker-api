package com.jshimdev0209.weatherchecker.location;

import com.jshimdev0209.weatherchecker.common.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {

    private LocationService locationService;

    public LocationApiController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        Location addedLocation = locationService.addLocation(location);
        URI uri = URI.create("/v1/locations/" + location.getCode());

        return ResponseEntity.created(uri).body(addedLocation);
    }
}
