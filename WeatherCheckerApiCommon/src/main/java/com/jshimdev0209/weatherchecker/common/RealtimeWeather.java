package com.jshimdev0209.weatherchecker.common;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "realtime_weather")
@Data
public class RealtimeWeather {

    @Id
    @Column(name = "location_code")
    private String locationCode;

    private int temperature;

    private int humidity;

    private int precipitation;

    private int windSpeed;
    @Column(length = 50)
    private String status;

    private Date lastUpdated;

    @OneToOne
    @JoinColumn(name = "location_code")
    @MapsId
    private Location location;
}
