package com.example.EY.application.patch.mapper;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PatchUsuarioMapperTest {

    private PatchUsuarioMapper patchUsuarioMapper;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        patchUsuarioMapper = new PatchUsuarioMapper();

        usuario = TestUtils.getUsuario(); // o puedes crear manualmente
        usuario.setId(UUID.randomUUID());
        usuario.setCreado(LocalDateTime.of(2024, 1, 1, 10, 0));
        usuario.setModificado(LocalDateTime.of(2024, 2, 1, 12, 0));
        usuario.setUltimoLogin(LocalDateTime.of(2024, 3, 1, 14, 0));
        usuario.setToken("token123");
        usuario.setActivo(true);
    }

    @Test
    void toResponse_deberiaMapearCorrectamente() {
        UsuarioResponseDto response = patchUsuarioMapper.toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getCreado(), response.getCreado());
        assertEquals(usuario.getModificado(), response.getModificado());
        assertEquals(usuario.getUltimoLogin(), response.getUltimoLogin());
        assertEquals(usuario.getToken(), response.getToken());
        assertEquals(usuario.isActivo(), response.isActivo());
    }
}