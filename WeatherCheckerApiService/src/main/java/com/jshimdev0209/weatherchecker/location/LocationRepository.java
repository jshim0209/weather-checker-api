package com.jshimdev0209.weatherchecker.location;

import com.jshimdev0209.weatherchecker.common.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, String> {

    @Query("SELECT l FROM Location l WHERE l.trashed = false")
    public List<Location> findUntrashedLocations();

    @Query("SELECT l FROM Location l WHERE l.trashed = false AND l.code =?1")
    public Location findByCode(String code);
}
