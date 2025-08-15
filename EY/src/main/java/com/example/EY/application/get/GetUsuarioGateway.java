package com.example.EY.application.get;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioResponseListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUsuarioGateway {

    private final GetUsuarioService getUsuarioService;

    public Optional<UsuarioResponseListDto> obtenerUsuario(UUID id) {
        return getUsuarioService.obtenerUsuario(id);
    }

    public List<UsuarioResponseListDto> listarUsuarios() {
        return getUsuarioService.listarUsuarios();
    }

    public Optional<Usuario> obtenerCorreo(String correo){
        return getUsuarioService.obtenerCorreo(correo);
    }
}
