package com.example.apptempo.dto;

import java.util.List;

public class ClimaResponseDTO {

    private CurrentData current;
    private List<DailyData> daily;

    // Getters e Setters
    public CurrentData getCurrent() { return current; }
    public void setCurrent(CurrentData current) { this.current = current; }
    public List<DailyData> getDaily() { return daily; }
    public void setDaily(List<DailyData> daily) { this.daily = daily; }


    public static class CurrentData {
        private String cityName; // Nome da cidade (vamos adicionar no parser)
        private String date; // Data formatada (vamos adicionar no parser)
        private Double temp;
        private Double feelsLike;
        private Integer humidity;
        private String description;
        private Long dt; // Timestamp

        // Getters e Setters
        public String getCityName() { return cityName; }
        public void setCityName(String cityName) { this.cityName = cityName; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public Double getTemp() { return temp; }
        public void setTemp(Double temp) { this.temp = temp; }
        public Double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(Double feelsLike) { this.feelsLike = feelsLike; }
        public Integer getHumidity() { return humidity; }
        public void setHumidity(Integer humidity) { this.humidity = humidity; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Long getDt() { return dt; }
        public void setDt(Long dt) { this.dt = dt; }
    }


    public static class DailyData {
        private String dayOfWeek;
        private String date;
        private Double max;
        private Double min;
        private String description;
        private Long dt;

        // Getters e Setters
        public String getDayOfWeek() { return dayOfWeek; }
        public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public Double getMax() { return max; }
        public void setMax(Double max) { this.max = max; }
        public Double getMin() { return min; }
        public void setMin(Double min) { this.min = min; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Long getDt() { return dt; }
        public void setDt(Long dt) { this.dt = dt; }
    }
}