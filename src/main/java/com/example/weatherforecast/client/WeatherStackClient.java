package com.example.weatherforecast.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class WeatherStackClient {

    @Value("${weather-stack.api.url}")
    private String apiUrl;

    @Value("${weather-stack.api.key}")
    private String apiKey;

    public Map<String, Object> getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(apiUrl + "?access_key=" + apiKey + "&query=" + city, Map.class);
        return Map.of(
                "temperature_degrees", response.get("current.temperature"),
                "wind_speed", response.get("current.wind_speed")
        );
    }
}