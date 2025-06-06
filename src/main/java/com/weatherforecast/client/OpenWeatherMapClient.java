package com.weatherforecast.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OpenWeatherMapClient {

    @Value("${open-weather-map.api.url}")
    private String apiUrl;

    @Value("${open-weather-map.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> getWeather(String city) {
        Map<String, Object> response = restTemplate.getForObject(apiUrl + "?q=" + city + "&appid=" + apiKey, Map.class);
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        Map<String, Object> wind = (Map<String, Object>) response.get("wind");
        return Map.of(
                "temperature_degrees", main.get("temp"),
                "wind_speed", wind.get("speed")
        );
    }
}