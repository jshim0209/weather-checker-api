package com.jshimdev0209.weatherchecker.realtime;

import com.jshimdev0209.weatherchecker.common.RealtimeWeather;
import org.springframework.data.repository.CrudRepository;

public interface RealtimeWeatherRepository extends CrudRepository<RealtimeWeather, String> {
}
