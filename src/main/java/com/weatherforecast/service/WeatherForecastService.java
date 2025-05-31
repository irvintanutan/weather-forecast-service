package com.weatherforecast.service;

import com.weatherforecast.dto.Weather;

public interface WeatherForecastService {
    /**
     * Retrieves the weather forecast for a given city.
     *
     * @param city the name of the city for which to retrieve the weather forecast
     * @return a map containing the weather forecast data
     */
    Weather getWeather(String city);
}
