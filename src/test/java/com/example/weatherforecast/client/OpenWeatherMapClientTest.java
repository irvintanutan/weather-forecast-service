package com.example.weatherforecast.client;

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

class OpenWeatherMapClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenWeatherMapClient openWeatherMapClient;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        Field apiUrlField = OpenWeatherMapClient.class.getDeclaredField("apiUrl");
        apiUrlField.setAccessible(true);
        apiUrlField.set(openWeatherMapClient, "http://mock-api-url.com");

        Field apiKeyField = OpenWeatherMapClient.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(openWeatherMapClient, "mock-api-key");
    }

    @Test
    void testGetWeather_thenReturnMapOfOpenWeather() {
        String city = "melbourne";
        String url = "http://mock-api-url.com?q=melbourne&appid=mock-api-key";

        Map<String, Object> mockResponse = Map.of(
                "main", Map.of("temp", 20.5),
                "wind", Map.of("speed", 5.0)
        );

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        Map<String, Object> result = openWeatherMapClient.getWeather(city);

        assertEquals(20.5, result.get("temperature_degrees"));
        assertEquals(5.0, result.get("wind_speed"));

        verify(restTemplate, times(1)).getForObject(url, Map.class);
    }
}