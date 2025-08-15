package com.example.EY.Infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank
    @Email
    private String correo;

    @NotBlank
    private String contraseña;
}
