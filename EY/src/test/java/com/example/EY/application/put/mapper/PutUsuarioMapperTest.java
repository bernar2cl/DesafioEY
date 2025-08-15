package com.example.EY.application.put.mapper;


import com.example.EY.Infrastructure.db.entity.Telefono;
import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PutUsuarioMapperTest {

    private PutUsuarioMapper mapper;
    private UsuarioRequestDto requestDto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        mapper = new PutUsuarioMapper();
        usuario = TestUtils.getUsuario(); // debe tener lista de telefonos inicializada
    }

    @Test
    void actualizarUsuarioDesdeRequest_deberiaActualizarCamposYTelefonos() {
        // Arrange
        requestDto = UsuarioRequestDto.builder()
                .nombre("Juan Actualizado")
                .correo("juan@dominio.cl")
                .contraseña("NewPass123")
                .build();

        UsuarioRequestDto.TelefonoRequest telefonoRequest = UsuarioRequestDto.TelefonoRequest.builder()
                .numero("123456789")
                .codigoCiudad("01")
                .codigoPais("+56")
                .build();

        requestDto.setTelefonos(Arrays.asList(telefonoRequest));

        // Act
        mapper.actualizarUsuarioDesdeRequest(usuario, requestDto);

        // Assert
        assertEquals("Juan Actualizado", usuario.getNombre());
        assertEquals("juan@dominio.cl", usuario.getCorreo());
        assertEquals("NewPass123", usuario.getContraseña());
        assertEquals(1, usuario.getTelefonos().size());

        Telefono telefono = usuario.getTelefonos().get(0);
        assertEquals("123456789", telefono.getNumero());
        assertEquals("01", telefono.getCodigoCiudad());
        assertEquals("+56", telefono.getCodigoPais());
        assertSame(usuario, telefono.getUsuario());
    }

    @Test
    void toResponse_deberiaMapearCorrectamente() {
        // Arrange
        usuario.setCreado(LocalDateTime.of(2024, 1, 1, 10, 0));
        usuario.setModificado(LocalDateTime.of(2024, 2, 1, 12, 0));
        usuario.setUltimoLogin(LocalDateTime.of(2024, 3, 1, 14, 0));
        usuario.setToken("token123");
        usuario.setActivo(true);

        // Act
        UsuarioResponseDto response = mapper.toResponse(usuario);

        // Assert
        assertEquals(usuario.getId(), response.getId());
        assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0), response.getCreado());
        assertEquals(LocalDateTime.of(2024, 2, 1, 12, 0), response.getModificado());
        assertEquals(LocalDateTime.of(2024, 3, 1, 14, 0), response.getUltimoLogin());
        assertEquals("token123", response.getToken());
        assertTrue(response.isActivo());
    }
}