package com.example.EY.application.del;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DelUsuarioGateway {
    private final DelUsuarioService delUsuarioService;

    public void eliminarUsuario(UUID id) {
        delUsuarioService.eliminarUsuario(id);
    }
}
