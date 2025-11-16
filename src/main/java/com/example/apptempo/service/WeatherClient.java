package com.example.apptempo.service;

import com.example.apptempo.dto.GeoResponseDTO;
import com.example.apptempo.dto.OneCallDTO;

public interface WeatherClient {
    GeoResponseDTO[] getCoordinates(String city);
    OneCallDTO getWeather(double lat, double lon);
}
