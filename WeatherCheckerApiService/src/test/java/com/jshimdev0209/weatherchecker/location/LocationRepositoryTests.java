package com.jshimdev0209.weatherchecker.location;

import com.jshimdev0209.weatherchecker.common.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTests {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testAddSuccess() {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Location savedLocation = locationRepository.save(location);

        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getCode()).isEqualTo("NYC_USA");

    }

    @Test
    public void testFindUntrashedSuccess() {
        List<Location> locations = locationRepository.findUntrashedLocations();

        assertThat(locations).isNotEmpty();

        locations.forEach(System.out::println);
    }

    @Test
    public void testGetLocationNotFound() {
        String code = "ABCD";
        Location location = locationRepository.findByCode(code);

        assertThat(location).isNull();
    }

    @Test
    public void testGetLocationFound() {
        String code = "NYC_USA";
        Location location = locationRepository.findByCode(code);

        assertThat(location).isNotNull();
        assertThat(location.getCode()).isEqualTo(code);
    }

    @Test
    public void testTrashSuccess() {
        String code = "LACA_USA";
        locationRepository.trashByCode(code);

        Location location = locationRepository.findByCode(code);

        assertThat(location).isNull();
    }
}
