package com.jshimdev0209.weatherchecker.realtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jshimdev0209.weatherchecker.GeolocationException;
import com.jshimdev0209.weatherchecker.GeolocationService;
import com.jshimdev0209.weatherchecker.common.Location;
import com.jshimdev0209.weatherchecker.common.RealtimeWeather;
import com.jshimdev0209.weatherchecker.location.LocationNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RealtimeWeatherApiController.class)
public class RealtimeWeatherApiControllerTests {

    private static final String END_POINT_PATH = "/v1/realtime";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    RealtimeWeatherService realtimeWeatherService;
    @MockBean
    GeolocationService geolocationService;

    @Test
    public void testGetRealtimeWeatherByIPAddressReturn400BadRequest() throws Exception {
        Mockito.when(geolocationService.getLocation(Mockito.anyString())).thenThrow(GeolocationException.class);

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testGetRealtimeWeatherByIPAddressReturn404NotFound() throws Exception {
        Location location = new Location();

        Mockito
                .when(geolocationService.getLocation(Mockito.anyString()))
                .thenReturn(location);

        Mockito
                .when(realtimeWeatherService.getByLocation(location))
                .thenThrow(LocationNotFoundException.class);

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetRealtimeWeatherByIPAddressReturn200OK() throws Exception {
        Location location = new Location();
        location.setCode("SFCA_USA");
        location.setCityName("San Francisco");
        location.setRegionName("California");
        location.setCountryName("United States of America");
        location.setCountryCode("US");

        RealtimeWeather realtimeWeather = new RealtimeWeather();
        realtimeWeather.setTemperature(12);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setLastUpdated(new Date());
        realtimeWeather.setPrecipitation(88);
        realtimeWeather.setStatus("Cloudy");
        realtimeWeather.setWindSpeed(5);

        realtimeWeather.setLocation(location);
        location.setRealtimeWeather(realtimeWeather);

        Mockito
                .when(geolocationService.getLocation(Mockito.anyString()))
                .thenReturn(location);

        Mockito
                .when(realtimeWeatherService.getByLocation(location))
                .thenReturn(realtimeWeather);

        String expectedLocation = location.getCityName()
                + ", " + location.getRegionName() + ", "
                + location.getCountryName();

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.location", is(expectedLocation)))
                .andDo(print());
    }
}
