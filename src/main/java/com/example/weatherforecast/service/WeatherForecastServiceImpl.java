package com.example.weatherforecast.service;

import com.example.weatherforecast.client.WeatherStackClient;
import com.example.weatherforecast.client.OpenWeatherMapClient;
import com.example.weatherforecast.dto.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {

    private final ConcurrentHashMap<String, Map<String, Object>> cache = new ConcurrentHashMap<>();
    private long lastCacheTime = 0;
    private static final String WIND_SPEED = "wind_speed";
    private static final String TEMPERATURE_DEGREES = "temperature_degrees";


    @Autowired
    private WeatherStackClient weatherStackClient;

    @Autowired
    private OpenWeatherMapClient openWeatherMapClient;

    @Override
    public Weather getWeather(String city) {
        long currentTime = System.currentTimeMillis();
        boolean isCacheValid = (currentTime - lastCacheTime < 3000) && cache.containsKey(city);

        if (isCacheValid) {
            Map<String, Object> cachedData = cache.get(city);
            return createWeatherFromData(cachedData);
        }

        Map<String, Object> weatherData = fetchWeatherData(city);
        cache.put(city, weatherData);
        lastCacheTime = currentTime;

        return createWeatherFromData(weatherData);
    }

    private Map<String, Object> fetchWeatherData(String city) {
        try {
            return weatherStackClient.getWeather(city);
        } catch (Exception e) {
            try {
                return openWeatherMapClient.getWeather(city);
            } catch (Exception ex) {
                return cache.getOrDefault(city, Map.of(WIND_SPEED, 0.0, TEMPERATURE_DEGREES, 0.0));
            }
        }
    }

    private Weather createWeatherFromData(Map<String, Object> data) {
        return new Weather(
                data.get(WIND_SPEED).toString(),
                data.get(TEMPERATURE_DEGREES).toString()
        );
    }
}