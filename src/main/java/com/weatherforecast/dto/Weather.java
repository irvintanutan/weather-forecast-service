package com.weatherforecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Weather {
    @JsonProperty("wind_speed")
    private String windSpeed;

    @JsonProperty("temperature_degrees")
    private String temperatureDegrees;

    public Weather() {
    }

    public Weather(String windSpeed, String temperatureDegrees) {
        this.windSpeed = windSpeed;
        this.temperatureDegrees = temperatureDegrees;
    }
}