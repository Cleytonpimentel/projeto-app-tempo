package com.example.apptempo.parser;

import com.example.apptempo.dto.ClimaResponseDTO;
import com.example.apptempo.dto.OneCallDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class OpenWeatherParser implements WeatherParser {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Locale PT_BR = new Locale("pt", "BR");
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    @Override
    public ClimaResponseDTO parse(OneCallDTO dto) {

        ClimaResponseDTO response = new ClimaResponseDTO();

        if (dto.current != null) {
            ClimaResponseDTO.CurrentData currentResponse = new ClimaResponseDTO.CurrentData();
            OneCallDTO.CurrentData currentDto = dto.current;

            currentResponse.setTemp(currentDto.temp);
            currentResponse.setFeelsLike(currentDto.feelsLike);
            currentResponse.setHumidity(currentDto.humidity);
            currentResponse.setDt(currentDto.dt);
            currentResponse.setDate(formatUnixTimestamp(currentDto.dt, DATE_FORMATTER));

            if (currentDto.weather != null && !currentDto.weather.isEmpty()) {
                currentResponse.setDescription(capitalize(currentDto.weather.get(0).description));
            }
            response.setCurrent(currentResponse);
        }

        List<ClimaResponseDTO.DailyData> dailyResponseList = new ArrayList<>();
        if (dto.daily != null) {
            for (OneCallDTO.DailyData dailyDto : dto.daily) {
                ClimaResponseDTO.DailyData dailyResponse = new ClimaResponseDTO.DailyData();

                dailyResponse.setDt(dailyDto.dt);
                dailyResponse.setDate(formatUnixTimestamp(dailyDto.dt, DATE_FORMATTER));
                dailyResponse.setDayOfWeek(getWeekDay(dailyDto.dt));

                if (dailyDto.temp != null) {
                    dailyResponse.setMin(dailyDto.temp.min);
                    dailyResponse.setMax(dailyDto.temp.max);
                }

                if (dailyDto.weather != null && !dailyDto.weather.isEmpty()) {
                    dailyResponse.setDescription(capitalize(dailyDto.weather.get(0).description));
                }

                dailyResponseList.add(dailyResponse);
            }
        }
        response.setDaily(dailyResponseList);

        return response;
    }

    private String formatUnixTimestamp(Long unixSeconds, DateTimeFormatter formatter) {
        if (unixSeconds == null) return null;
        try {
            Instant instant = Instant.ofEpochSecond(unixSeconds);
            return instant.atZone(ZONE_ID).format(formatter);
        } catch (Exception e) {
            System.err.println("Erro ao formatar timestamp: " + unixSeconds + " - " + e.getMessage());
            return null;
        }
    }

    private String getWeekDay(Long unixSeconds) {
        if (unixSeconds == null) return null;
        try {
            Instant instant = Instant.ofEpochSecond(unixSeconds);
            LocalDate date = instant.atZone(ZONE_ID).toLocalDate();
            String day = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, PT_BR);
            return capitalize(day.replace(".", ""));
        } catch (Exception e) {
            System.err.println("Erro ao obter dia da semana: " + unixSeconds + " - " + e.getMessage());
            return null;
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}