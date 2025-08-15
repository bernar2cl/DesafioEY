package com.example.EY;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestUtils {

    public static Usuario getUsuario(){
        Usuario usuario;
        UUID usuarioId;

        usuarioId = UUID.randomUUID();
        usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setNombre("Juan");
        usuario.setCorreo("juan@dominio.cl");
        usuario.setContraseña("Password123");
        usuario.setActivo(true);
        usuario.setCreado(LocalDateTime.now());

        return usuario;
    }

}
