package com.jshimdev0209.weatherchecker.realtime;

import com.jshimdev0209.weatherchecker.common.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RealtimeWeatherRepositoryTest {

    @Autowired
    private RealtimeWeatherRepository realtimeWeatherRepository;

    @Test
    public void testUpdateRealtimeWeather() {
        String locationCode = "NYC_USA";

        RealtimeWeather realtimeWeather = realtimeWeatherRepository.findById(locationCode).get();

        realtimeWeather.setTemperature(-2);
        realtimeWeather.setHumidity(32);
        realtimeWeather.setPrecipitation(42);
        realtimeWeather.setStatus("Snowy");
        realtimeWeather.setWindSpeed(12);
        realtimeWeather.setLastUpdated(new Date());

        RealtimeWeather updatedRealtimeWeather = realtimeWeatherRepository.save(realtimeWeather);

        assertThat(updatedRealtimeWeather.getHumidity()).isEqualTo(32);
    }
}
