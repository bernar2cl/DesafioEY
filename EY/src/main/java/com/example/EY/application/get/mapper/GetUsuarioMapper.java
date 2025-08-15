package com.example.EY.application.get.mapper;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetUsuarioMapper {
    public UsuarioResponseListDto toResponseList(Usuario usuario) {
        return UsuarioResponseListDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .correo(usuario.getCorreo())
                .contraseña(usuario.getContraseña())
                .telefonos(usuario.getTelefonos().stream()
                        .map(t -> {
                            UsuarioResponseListDto.TelefonoRequest telefono = new UsuarioResponseListDto.TelefonoRequest();
                            telefono.setNumero(t.getNumero());
                            telefono.setCodigoCiudad(t.getCodigoCiudad());
                            telefono.setCodigoPais(t.getCodigoPais());
                            return telefono;
                        })
                        .collect(Collectors.toList()))
                .creado(usuario.getCreado())
                .modificado(usuario.getModificado())
                .ultimoLogin(usuario.getUltimoLogin())
                .token(usuario.getToken())
                .activo(usuario.isActivo())
                .role(usuario.getRole())
                .build();
    }
}
