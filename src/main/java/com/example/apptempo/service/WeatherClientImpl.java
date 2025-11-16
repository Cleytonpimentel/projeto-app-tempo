package com.example.apptempo.service;

import com.example.apptempo.dto.GeoResponseDTO;
import com.example.apptempo.dto.OneCallDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class WeatherClientImpl implements WeatherClient {

    @Value("${weather.api.geo.url}")
    private String geoUrl;

    @Value("${weather.api.onecall.url}")
    private String oneCallUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GeoResponseDTO[] getCoordinates(String city) {
        var uri = UriComponentsBuilder.fromHttpUrl(geoUrl)
                .queryParam("q", city)
                .queryParam("limit", 1)
                .queryParam("appid", apiKey)
                .build()
                .toUri();
        return restTemplate.getForObject(uri, GeoResponseDTO[].class);
    }

    @Override
    public OneCallDTO getWeather(double lat, double lon) {
        var uri = UriComponentsBuilder.fromHttpUrl(oneCallUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .queryParam("lang", "pt_br")
                .queryParam("exclude", "minutely,hourly,alerts")
                .build()
                .toUri();
        return restTemplate.getForObject(uri, OneCallDTO.class);
    }
}
