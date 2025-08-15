package com.example.EY.application.put;


import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PutUsuarioGateway {
    private final PutUsuarioService putUsuarioService;

    public UsuarioResponseDto actualizarUsuario(UUID id, UsuarioRequestDto request) {
        return  putUsuarioService.actualizarUsuario(id, request);
    }

    public UsuarioResponseDto actualizarToken(Usuario usuario) {
        return putUsuarioService.actualizarToken(usuario);
    }
}
