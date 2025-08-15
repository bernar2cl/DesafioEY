package com.example.EY.application.post.mapper;

import com.example.EY.Infrastructure.db.entity.Telefono;
import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostUsuarioMapperTest {

    private PostUsuarioMapper mapper;

    private UsuarioRequestDto requestDto;
    private LocalDateTime timestamp;

    @BeforeEach
    void setUp() {
        mapper = new PostUsuarioMapper();

        requestDto = UsuarioRequestDto.builder()
                .nombre("Juan")
                .correo("juan@dominio.cl")
                .contraseña("Pass1234")
                .build();

        UsuarioRequestDto requestDto = UsuarioRequestDto.builder()
                .nombre("Juan")
                .correo("juan@dominio.cl")
                .contraseña("Pass123")
                .build();

        // Agregamos teléfonos
        UsuarioRequestDto.TelefonoRequest telefono1 = UsuarioRequestDto.TelefonoRequest.builder()
                .numero("123456789")
                .codigoCiudad("01")
                .codigoPais("+56")
                .build();

        UsuarioRequestDto.TelefonoRequest telefono2 = UsuarioRequestDto.TelefonoRequest.builder()
                .numero("987654321")
                .codigoCiudad("02")
                .codigoPais("+56")
                .build();

        requestDto.setTelefonos(Arrays.asList(telefono1, telefono2));



        timestamp = LocalDateTime.now();
    }

    @Test
    void toEntity_deberiaMapearCorrectamente() {
        mapper = new PostUsuarioMapper();

        requestDto = UsuarioRequestDto.builder()
                .nombre("Juan")
                .correo("juan@dominio.cl")
                .contraseña("Pass123")
                .build();

        // Agregamos teléfonos
        UsuarioRequestDto.TelefonoRequest telefono1 = UsuarioRequestDto.TelefonoRequest.builder()
                .numero("123456789")
                .codigoCiudad("01")
                .codigoPais("+56")
                .build();

        UsuarioRequestDto.TelefonoRequest telefono2 = UsuarioRequestDto.TelefonoRequest.builder()
                .numero("987654321")
                .codigoCiudad("02")
                .codigoPais("+56")
                .build();

        requestDto.setTelefonos(Arrays.asList(telefono1, telefono2));

        timestamp = LocalDateTime.now();
    }

    @Test
    void toResponse_deberiaMapearCorrectamente() {
        Usuario usuario = mapper.toEntity(requestDto, timestamp);
        usuario.setId(java.util.UUID.randomUUID());
        usuario.setToken("token123");

        UsuarioResponseDto response = mapper.toResponse(usuario);

        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getCreado(), response.getCreado());
        assertEquals(usuario.getModificado(), response.getModificado());
        assertEquals(usuario.getUltimoLogin(), response.getUltimoLogin());
        assertEquals(usuario.getToken(), response.getToken());
        assertEquals(usuario.isActivo(), response.isActivo());
    }
}