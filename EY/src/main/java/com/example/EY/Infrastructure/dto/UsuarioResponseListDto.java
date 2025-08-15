package com.example.EY.Infrastructure.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UsuarioResponseListDto {
    private UUID id;
    private String nombre;
    private String correo;
    private String contraseña;
    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoLogin;
    private String token;
    private boolean activo;
    private String role;

    private List<TelefonoRequest> telefonos;

    @Data
    public static class TelefonoRequest {
        private String numero;
        private String codigoCiudad;
        private String codigoPais;
    }
}
