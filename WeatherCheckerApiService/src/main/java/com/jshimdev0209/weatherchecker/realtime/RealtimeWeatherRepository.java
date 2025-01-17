package com.jshimdev0209.weatherchecker.realtime;

import com.jshimdev0209.weatherchecker.common.RealtimeWeather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RealtimeWeatherRepository extends CrudRepository<RealtimeWeather, String> {

    @Query("SELECT r FROM RealtimeWeather r WHERE r.location.countryCode = ?1 AND r.location.cityName =?2")
    public RealtimeWeather findByCountryCodeAndCity(String countryCode, String city);
}
