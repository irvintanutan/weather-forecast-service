package com.example.weatherforecast.service;

import com.example.weatherforecast.client.WeatherStackClient;
import com.example.weatherforecast.client.OpenWeatherMapClient;
import com.example.weatherforecast.dto.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherForecastServiceTest {

    @Mock
    private WeatherStackClient weatherStackClient;

    @Mock
    private OpenWeatherMapClient openWeatherMapClient;

    @InjectMocks
    private WeatherForecastServiceImpl weatherForecastService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeather_fromWeatherStackClient() {
        String city = "melbourne";
        Map<String, Object> mockResponse = Map.of(
                "temperature_degrees", 22.0,
                "wind_speed", 10.0
        );

        when(weatherStackClient.getWeather(city)).thenReturn(mockResponse);

        Weather result = weatherForecastService.getWeather(city);

        assertEquals("22.0", result.getTemperatureDegrees());
        assertEquals("10.0", result.getWindSpeed());

        verify(weatherStackClient, times(1)).getWeather(city);
        verify(openWeatherMapClient, never()).getWeather(city);
    }

    @Test
    void testGetWeather_fallbackToOpenWeatherMapClient() {
        String city = "melbourne";
        Map<String, Object> mockResponse = Map.of(
                "temperature_degrees", 18.0,
                "wind_speed", 8.0
        );

        when(weatherStackClient.getWeather(city)).thenThrow(new RuntimeException("WeatherStack error"));
        when(openWeatherMapClient.getWeather(city)).thenReturn(mockResponse);

        Weather result = weatherForecastService.getWeather(city);

        assertEquals("18.0", result.getTemperatureDegrees());
        assertEquals("8.0", result.getWindSpeed());

        verify(weatherStackClient, times(1)).getWeather(city);
        verify(openWeatherMapClient, times(1)).getWeather(city);
    }

    @Test
    void testGetWeather_fromCache() {
        String city = "melbourne";
        Map<String, Object> mockResponse = Map.of(
                "temperature_degrees", 22.0,
                "wind_speed", 10.0
        );

        when(weatherStackClient.getWeather(city)).thenReturn(mockResponse);

        // First call to populate the cache
        Weather firstResult = weatherForecastService.getWeather(city);
        assertEquals("22.0", firstResult.getTemperatureDegrees());
        assertEquals("10.0", firstResult.getWindSpeed());

        // Second call should use the cache
        Weather cachedResult = weatherForecastService.getWeather(city);
        assertEquals("22.0", cachedResult.getTemperatureDegrees());
        assertEquals("10.0", cachedResult.getWindSpeed());

        verify(weatherStackClient, times(1)).getWeather(city);
        verify(openWeatherMapClient, never()).getWeather(city);
    }

    @Test
    void testGetWeather_defaultValuesWhenBothClientsFail() {
        String city = "melbourne";

        when(weatherStackClient.getWeather(city)).thenThrow(new RuntimeException("WeatherStack error"));
        when(openWeatherMapClient.getWeather(city)).thenThrow(new RuntimeException("OpenWeatherMap error"));

        Weather result = weatherForecastService.getWeather(city);

        assertEquals("0.0", result.getTemperatureDegrees());
        assertEquals("0.0", result.getWindSpeed());

        verify(weatherStackClient, times(1)).getWeather(city);
        verify(openWeatherMapClient, times(1)).getWeather(city);
    }
}