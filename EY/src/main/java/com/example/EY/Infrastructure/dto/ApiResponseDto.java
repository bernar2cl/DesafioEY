package com.example.EY.Infrastructure.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDto<T> {
    private T data;
    private String mensaje;

    public ApiResponseDto(T data, String mensaje) {
        this.data = data;
        this.mensaje = mensaje;
    }
}
