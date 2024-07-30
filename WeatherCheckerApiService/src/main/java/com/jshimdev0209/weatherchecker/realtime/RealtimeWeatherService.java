package com.jshimdev0209.weatherchecker.realtime;

import com.jshimdev0209.weatherchecker.common.Location;
import com.jshimdev0209.weatherchecker.common.RealtimeWeather;
import com.jshimdev0209.weatherchecker.location.LocationNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RealtimeWeatherService {

    private final RealtimeWeatherRepository realtimeWeatherRepository;

    public RealtimeWeatherService(RealtimeWeatherRepository realtimeWeatherRepository) {
        super();
        this.realtimeWeatherRepository = realtimeWeatherRepository;
    }

    public RealtimeWeather getByLocation(Location location) throws LocationNotFoundException {
        String countryCode = location.getCountryCode();
        String cityName = location.getCityName();

        RealtimeWeather realtimeWeather = realtimeWeatherRepository.findByCountryCodeAndCity(countryCode, cityName);

        if (realtimeWeather == null) {
            throw new LocationNotFoundException("No location found with the given country code and city name");
        }
        return realtimeWeather;
    }
}
