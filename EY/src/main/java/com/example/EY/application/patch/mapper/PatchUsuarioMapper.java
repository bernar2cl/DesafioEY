package com.example.EY.application.patch.mapper;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatchUsuarioMapper {
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
