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

    @Autowired
    private WeatherStackClient weatherStackClient;

    @Autowired
    private OpenWeatherMapClient openWeatherMapClient;

    private final ConcurrentHashMap<String, Map<String, Object>> cache = new ConcurrentHashMap<>();
    private final long CACHE_EXPIRY = 3000; // 3 seconds
    private long lastCacheTime = 0;

    @Override
    public Weather getWeather(String city) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCacheTime < CACHE_EXPIRY && cache.containsKey(city)) {
            Map<String, Object> cachedData = cache.get(city);
            return new Weather(
                    (double) cachedData.get("wind_speed"),
                    (double) cachedData.get("temperature_degrees")
            );
        }

        try {
            Map<String, Object> weatherData = weatherStackClient.getWeather(city);
            cache.put(city, weatherData);
            lastCacheTime = currentTime;
            return new Weather(
                    (double) weatherData.get("wind_speed"),
                    (double) weatherData.get("temperature_degrees")
            );
        } catch (Exception e) {
            try {
                Map<String, Object> weatherData = openWeatherMapClient.getWeather(city);
                cache.put(city, weatherData);
                lastCacheTime = currentTime;
                return new Weather(
                        (double) weatherData.get("wind_speed"),
                        (double) weatherData.get("temperature_degrees")
                );
            } catch (Exception ex) {
                Map<String, Object> cachedData = cache.getOrDefault(city, Map.of("wind_speed", 0.0, "temperature_degrees", 0.0));
                return new Weather(
                        (double) cachedData.get("wind_speed"),
                        (double) cachedData.get("temperature_degrees")
                );
            }
        }
    }
}