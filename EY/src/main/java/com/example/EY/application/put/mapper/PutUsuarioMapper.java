package com.example.EY.application.put.mapper;


import com.example.EY.Infrastructure.db.entity.Telefono;
import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PutUsuarioMapper {

    public void actualizarUsuarioDesdeRequest(Usuario usuario, UsuarioRequestDto request) {
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setContraseña(request.getContraseña());

        if (request.getTelefonos() != null && !request.getTelefonos().isEmpty()) {
            List<Telefono> nuevosTelefonos = request.getTelefonos().stream()
                    .map(t -> new Telefono(null, t.getNumero(), t.getCodigoCiudad(), t.getCodigoPais(), usuario))
                    .collect(Collectors.toList());
            usuario.getTelefonos().clear();
            usuario.getTelefonos().addAll(nuevosTelefonos);
        }
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
