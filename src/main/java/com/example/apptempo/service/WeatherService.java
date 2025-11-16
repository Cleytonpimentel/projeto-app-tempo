package com.example.apptempo.service;

import com.example.apptempo.dto.ClimaResponseDTO;

public interface WeatherService {
    ClimaResponseDTO buscarClima(String cidade);
}
