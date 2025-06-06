package com.weatherforecast.controller;

import com.weatherforecast.dto.Weather;
import com.weatherforecast.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherForecastController {

    @Autowired
    private WeatherForecastService weatherForecastService;

    @GetMapping("/v1/weather")
    public Weather getWeather(@RequestParam(defaultValue = "melbourne") String city) {
        return weatherForecastService.getWeather(city);
    }
}