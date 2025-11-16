package com.example.apptempo.provider;

import com.example.apptempo.dto.GeoResponseDTO;
import com.example.apptempo.dto.OneCallDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class OpenWeatherProvider {

    @Value("${weather.api.geo.url}")
    private String geoApiUrl;

    @Value("${weather.api.onecall.url}")
    private String oneCallApiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OpenWeatherProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeoResponseDTO buscarCoordenadas(String cidade) {

        URI uri = UriComponentsBuilder.fromHttpUrl(geoApiUrl)
                .queryParam("q", cidade)
                .queryParam("limit", 1)
                .queryParam("appid", apiKey)
                .build()
                .toUri();

        GeoResponseDTO[] resposta = restTemplate.getForObject(uri, GeoResponseDTO[].class);

        if (resposta == null || resposta.length == 0) {
            throw new RuntimeException("Cidade não encontrada: " + cidade);
        }

        return resposta[0];
    }

    public OneCallDTO buscarClima(double lat, double lon) {

        URI uri = UriComponentsBuilder.fromHttpUrl(oneCallApiUrl)
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
