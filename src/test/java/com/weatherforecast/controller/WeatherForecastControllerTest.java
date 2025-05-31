package com.weatherforecast.controller;

import com.weatherforecast.dto.Weather;
import com.weatherforecast.service.WeatherForecastService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WeatherForecastControllerTest {

    @Mock
    private WeatherForecastService weatherForecastService;

    @InjectMocks
    private WeatherForecastController weatherForecastController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherForecastController).build();
    }

    @Test
    void testGetWeather_thenReturnSuccess() throws Exception {
        String city = "melbourne";
        Weather mockWeather = new Weather("20.5", "5.0");

        when(weatherForecastService.getWeather(city)).thenReturn(mockWeather);

        mockMvc.perform(get("/v1/weather")
                        .param("city", city)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature_degrees").value(5.0))
                .andExpect(jsonPath("$.wind_speed").value(20.5));

        verify(weatherForecastService, times(1)).getWeather(city);
    }
}