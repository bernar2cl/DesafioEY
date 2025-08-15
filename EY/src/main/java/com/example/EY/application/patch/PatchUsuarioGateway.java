package com.example.EY.application.patch;

import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatchUsuarioGateway {

    private final PatchUsuarioService patchUsuarioService;

    public UsuarioResponseDto activarUsuario(UUID id){
        return patchUsuarioService.activarUsuario(id);
    }

    public UsuarioResponseDto desactivarUsuario(UUID id){
        return patchUsuarioService.desactivarUsuario(id);
    }

}
