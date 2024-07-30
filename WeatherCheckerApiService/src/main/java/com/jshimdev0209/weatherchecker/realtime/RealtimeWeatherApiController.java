package com.jshimdev0209.weatherchecker.realtime;

import com.jshimdev0209.weatherchecker.CommonUtility;
import com.jshimdev0209.weatherchecker.GeolocationException;
import com.jshimdev0209.weatherchecker.GeolocationService;
import com.jshimdev0209.weatherchecker.common.Location;
import com.jshimdev0209.weatherchecker.common.RealtimeWeather;
import com.jshimdev0209.weatherchecker.location.LocationNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/realtime")
public class RealtimeWeatherApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeWeatherApiController.class);

    private final GeolocationService geolocationService;
    private final RealtimeWeatherService realtimeWeatherService;
    private final ModelMapper modelMapper;

    public RealtimeWeatherApiController(GeolocationService geolocationService, RealtimeWeatherService realtimeWeatherService, ModelMapper modelMapper) {
        super();
        this.geolocationService = geolocationService;
        this.realtimeWeatherService = realtimeWeatherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<?> getRealtimeWeatherByIPAddress(HttpServletRequest request) {
        String ipAddress = CommonUtility.getIPAddress(request);

        try {
            Location locationFromIP = geolocationService.getLocation(ipAddress);
            RealtimeWeather realtimeWeather = realtimeWeatherService.getByLocation(locationFromIP);

            RealtimeWeatherDTO realtimeWeatherDTO = modelMapper.map(realtimeWeather, RealtimeWeatherDTO.class);

            return ResponseEntity.ok(realtimeWeatherDTO);

        } catch (GeolocationException exception) {
            LOGGER.error(exception.getMessage(), exception);

            return ResponseEntity.badRequest().build();

        } catch (LocationNotFoundException exception) {
            LOGGER.error(exception.getMessage(), exception);

            return ResponseEntity.notFound().build();
        }
    }
}
