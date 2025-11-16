package com.example.apptempo.provider;

import com.example.apptempo.dto.GeoResponseDTO;
import com.example.apptempo.dto.OneCallDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherProvider {

    private final RestTemplate restTemplate;

    public WeatherProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeoResponseDTO[] fetchGeo(String city, String apiKey) {
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + city
                + "&limit=1&appid=" + apiKey;
        return restTemplate.getForObject(url, GeoResponseDTO[].class);
    }

    public OneCallDTO fetchWeather(double lat, double lon, String apiKey) {
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat
                + "&lon=" + lon + "&appid=" + apiKey + "&units=metric&lang=pt_br";
        return restTemplate.getForObject(url, OneCallDTO.class);
    }
}
