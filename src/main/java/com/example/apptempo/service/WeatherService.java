package com.example.apptempo.service;

import com.example.apptempo.dto.ForecastResponseDTO;
import com.example.apptempo.dto.GeoResponseDTO;
import com.example.apptempo.dto.OneCallDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Value("${weather.api.geo.url}")
    private String geoApiUrl;
    @Value("${weather.api.onecall.url}")
    private String oneCallApiUrl;
    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final Locale brasilLocale = new Locale("pt", "BR");

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ForecastResponseDTO getForecastForCity(String city) {
        String cityClean = city.trim();
        GeoResponseDTO geoData = getCoordinatesForCity(cityClean);

        if (geoData == null) {
            System.out.println("ERRO 404 (Geo): Cidade não encontrada: " + cityClean);
            return null;
        }

        try {
            URI oneCallUri = UriComponentsBuilder.fromHttpUrl(oneCallApiUrl)
                    .queryParam("lat", geoData.lat)
                    .queryParam("lon", geoData.lon)
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .queryParam("lang", "pt_br")
                    .queryParam("exclude", "minutely,hourly,alerts")
                    .build().toUri();

            System.out.println("Buscando Previsão: " + oneCallUri);
            OneCallDTO oneCallResponse = restTemplate.getForObject(oneCallUri, OneCallDTO.class);
            return mapToForecastResponse(oneCallResponse, geoData.name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private GeoResponseDTO getCoordinatesForCity(String city) {
        try {
            URI geoUri = UriComponentsBuilder.fromHttpUrl(geoApiUrl)
                    .queryParam("q", city)
                    .queryParam("limit", 1)
                    .queryParam("appid", apiKey)
                    .build().toUri();
            GeoResponseDTO[] response = restTemplate.getForObject(geoUri, GeoResponseDTO[].class);
            if (response != null && response.length > 0) return response[0];
        } catch (Exception ignored) { }
        return null;
    }

    private ForecastResponseDTO mapToForecastResponse(OneCallDTO raw, String cityName) {
        if (raw == null || raw.current == null || raw.daily == null) return null;

        // Formato de data: 10/11/2025
        var formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Data atual (para o "current")
        String currentDate = java.time.LocalDate.now().format(formatter);

        // Previsão atual
        ForecastResponseDTO.CurrentWeather current = new ForecastResponseDTO.CurrentWeather(
                cityName,
                raw.current.temp,
                raw.current.feelsLike,
                raw.current.humidity,
                raw.current.weather.isEmpty() ? "N/A" : raw.current.weather.get(0).description,
                currentDate // <-- adiciona a data de hoje
        );

        // Previsões dos próximos dias
        List<ForecastResponseDTO.DailyForecast> daily = raw.daily.stream()
                .skip(1) // pula o dia atual
                .limit(7) // limita a 7 dias
                .map(d -> {
                    LocalDate data = Instant.ofEpochSecond(d.dt)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    String dayOfWeek = data.getDayOfWeek()
                            .getDisplayName(TextStyle.SHORT, brasilLocale); // ex: "Ter."
                    String dateFormatted = data.format(formatter); // ex: "11/11/2025"

                    return new ForecastResponseDTO.DailyForecast(
                            dayOfWeek,
                            dateFormatted, // <-- data correspondente
                            d.temp.min,
                            d.temp.max,
                            d.weather.isEmpty() ? "N/A" : d.weather.get(0).description
                    );
                })
                .collect(Collectors.toList());

        return new ForecastResponseDTO(current, daily);
    }

    private String formatTimestampToDayOfWeek(long timestamp) {
        return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault())
                .toLocalDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, brasilLocale);
    }
}