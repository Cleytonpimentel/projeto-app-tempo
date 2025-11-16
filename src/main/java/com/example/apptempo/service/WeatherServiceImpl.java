package com.example.apptempo.service;

import com.example.apptempo.dto.ClimaResponseDTO;
import com.example.apptempo.dto.GeoResponseDTO;
import com.example.apptempo.dto.OneCallDTO;
import com.example.apptempo.exception.CidadeNaoEncontradaException;
import com.example.apptempo.parser.WeatherParser;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient client;
    private final WeatherParser parser;

    public WeatherServiceImpl(WeatherClient client, WeatherParser parser) {
        this.client = client;
        this.parser = parser;
    }

    @Override
    public ClimaResponseDTO buscarClima(String cidade) {

        GeoResponseDTO[] coordinates = client.getCoordinates(cidade);

        if (coordinates == null || coordinates.length == 0) {
            throw new CidadeNaoEncontradaException("Não foi possível encontrar coordenadas para a cidade: " + cidade);
        }

        GeoResponseDTO geo = coordinates[0];

        OneCallDTO oneCall = client.getWeather(geo.getLat(), geo.getLon());

        ClimaResponseDTO response = parser.parse(oneCall);

        if (response.getCurrent() != null) {
            String nomeDaCidade = (geo.getName() != null) ? geo.getName() : cidade;
            response.getCurrent().setCityName(capitalize(nomeDaCidade));
        }

        return response;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}