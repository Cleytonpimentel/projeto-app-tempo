package com.example.apptempo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneCallDTO {
    @JsonProperty("current")
    public CurrentData current;
    @JsonProperty("daily")
    public List<DailyData> daily;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentData {
        public Double temp;
        @JsonProperty("feels_like")
        public Double feelsLike;
        public Integer humidity;
        public List<WeatherData> weather;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DailyData {
        public Long dt;
        public TempData temp;
        public List<WeatherData> weather;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TempData {
        public Double min;
        public Double max;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherData {
        public String description;
    }
}