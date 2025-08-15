package com.example.EY.application.post.mapper;


import com.example.EY.Infrastructure.db.entity.Telefono;
import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostUsuarioMapper {
    public Usuario toEntity(UsuarioRequestDto request, LocalDateTime timestamp) {
        return Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .contraseña(request.getContraseña())
                .creado(timestamp)
                .modificado(timestamp)
                .ultimoLogin(timestamp)
                .activo(true)
                .token(null)
                .telefonos(
                        request.getTelefonos() == null || request.getTelefonos().isEmpty()
                                ? Collections.emptyList()
                                : request.getTelefonos().stream()
                                .map(t -> new Telefono(null, t.getNumero(), t.getCodigoCiudad(), t.getCodigoPais(), null))
                                .collect(Collectors.toList())
                )
                .build();
    }

    public UsuarioResponseDto toResponse(Usuario usuario) {
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .creado(usuario.getCreado())
                .modificado(usuario.getModificado())
                .ultimoLogin(usuario.getUltimoLogin())
                .token(usuario.getToken())
                .activo(usuario.isActivo())
                .build();
    }
}
