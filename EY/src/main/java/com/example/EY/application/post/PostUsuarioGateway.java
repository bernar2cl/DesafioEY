package com.example.EY.application.post;

import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PostUsuarioGateway {
    private final PostUsuarioService postUsuarioService;

    public UsuarioResponseDto crearUsuario(UsuarioRequestDto request) {
        return postUsuarioService.crearUsuario(request);
    }
}
