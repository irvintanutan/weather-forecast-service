package com.example.weatherforecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Weather(
        @JsonProperty("wind_speed") double windSpeed,
        @JsonProperty("temperature_degrees") double temperatureDegrees
) {
}