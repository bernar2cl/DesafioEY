package com.example.EY.application.get.mapper;

import com.example.EY.Infrastructure.db.entity.Telefono;
import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioResponseListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetUsuarioMapperTest {

    private GetUsuarioMapper getUsuarioMapper;

    private Usuario usuario;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        getUsuarioMapper = new GetUsuarioMapper();
        usuarioId = UUID.randomUUID();

        Telefono telefono = Telefono.builder()
                .numero("12345678")
                .codigoCiudad("+2")
                .codigoPais("+56")
                .build();

        usuario = Usuario.builder()
                .id(usuarioId)
                .nombre("Juan Pérez")
                .correo("juan@example.com")
                .contraseña("password123")
                .telefonos(List.of(telefono))
                .creado(LocalDateTime.now())
                .modificado(LocalDateTime.now())
                .ultimoLogin(LocalDateTime.now())
                .token("token123")
                .activo(true)
                .role("USER")
                .build();
    }

    @Test
    void testToResponseList() {
        UsuarioResponseListDto dto = getUsuarioMapper.toResponseList(usuario);

        assertNotNull(dto);
        assertEquals(usuario.getId(), dto.getId());
        assertEquals(usuario.getNombre(), dto.getNombre());
        assertEquals(usuario.getCorreo(), dto.getCorreo());
        assertEquals(usuario.getContraseña(), dto.getContraseña());
        assertEquals(usuario.getCreado(), dto.getCreado());
        assertEquals(usuario.getModificado(), dto.getModificado());
        assertEquals(usuario.getUltimoLogin(), dto.getUltimoLogin());
        assertEquals(usuario.getToken(), dto.getToken());
        assertEquals(usuario.isActivo(), dto.isActivo());
        assertEquals(usuario.getRole(), dto.getRole());

        // Verificar teléfonos
        assertNotNull(dto.getTelefonos());
        assertEquals(1, dto.getTelefonos().size());
        UsuarioResponseListDto.TelefonoRequest telefonoDto = dto.getTelefonos().get(0);
        Telefono telefonoOriginal = usuario.getTelefonos().get(0);

        assertEquals(telefonoOriginal.getNumero(), telefonoDto.getNumero());
        assertEquals(telefonoOriginal.getCodigoCiudad(), telefonoDto.getCodigoCiudad());
        assertEquals(telefonoOriginal.getCodigoPais(), telefonoDto.getCodigoPais());
    }
}
