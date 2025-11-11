package com.example.apptempo.controller;

import com.example.apptempo.dto.ForecastResponseDTO;
import com.example.apptempo.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<ForecastResponseDTO> getForecast(@RequestParam String city) {
        ForecastResponseDTO forecast = weatherService.getForecastForCity(city);
        if (forecast != null) {
            return ResponseEntity.ok(forecast);
        }
        return ResponseEntity.notFound().build();
    }
}