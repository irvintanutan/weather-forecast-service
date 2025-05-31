package com.weatherforecast.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherStackClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherStackClient weatherStackClient;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        Field apiUrlField = WeatherStackClient.class.getDeclaredField("apiUrl");
        apiUrlField.setAccessible(true);
        apiUrlField.set(weatherStackClient, "http://mock-api-url.com");

        Field apiKeyField = WeatherStackClient.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(weatherStackClient, "mock-api-key");
    }

    @Test
    void testGetWeather_thenReturnMapOfWeatherStack() {
        String city = "melbourne";

        Map<String, Object> mockResponse = Map.of(
                "current", Map.of(
                        "temperature", 22.0,
                        "wind_speed", 10.0
                )
        );

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        Map<String, Object> result = weatherStackClient.getWeather(city);

        assertEquals(22.0, result.get("temperature_degrees"));
        assertEquals(10.0, result.get("wind_speed"));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));
    }
}