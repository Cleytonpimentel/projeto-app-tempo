package com.example.apptempo.dto;

import java.util.List;

public record ForecastResponseDTO(
        CurrentWeather current,
        List<DailyForecast> daily
) {
    public record CurrentWeather(
            String cityName,
            Double temp,
            Double feelsLike,
            Integer humidity,
            String description,
            String date // <-- adicionamos a data do dia atual
    ) {}

    public record DailyForecast(
            String dayOfWeek,
            String date, // <-- adicionamos a data de cada dia
            Double minTemp,
            Double maxTemp,
            String description
    ) {}
}
