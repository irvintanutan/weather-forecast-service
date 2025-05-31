package com.weatherforecast.client;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> getWeather(String city) {
        Map<String, Object> response = restTemplate.getForObject(apiUrl + "?access_key=" + apiKey + "&query=" + city, Map.class);
        Map<String, Object> current = (Map<String, Object>) response.get("current");
        return Map.of(
                "temperature_degrees", current.get("temperature"),
                "wind_speed", current.get("wind_speed")
        );
    }
}