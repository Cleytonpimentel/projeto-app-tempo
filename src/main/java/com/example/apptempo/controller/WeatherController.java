package com.example.apptempo.controller;

import com.example.apptempo.dto.ClimaResponseDTO;
import com.example.apptempo.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping
    public ResponseEntity<ClimaResponseDTO> buscarClima(@RequestParam("city") String city) {

        ClimaResponseDTO clima = weatherService.buscarClima(city);
        return ResponseEntity.ok(clima);
    }
}