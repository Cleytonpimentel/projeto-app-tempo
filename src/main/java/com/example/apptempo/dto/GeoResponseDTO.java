package com.example.apptempo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResponseDTO {
    public double lat;
    public double lon;
    public String name;
}