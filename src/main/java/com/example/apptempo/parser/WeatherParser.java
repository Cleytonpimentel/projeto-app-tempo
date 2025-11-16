package com.example.apptempo.parser;

import com.example.apptempo.dto.ClimaResponseDTO;
import com.example.apptempo.dto.OneCallDTO;

public interface WeatherParser {
    ClimaResponseDTO parse(OneCallDTO dto);
}