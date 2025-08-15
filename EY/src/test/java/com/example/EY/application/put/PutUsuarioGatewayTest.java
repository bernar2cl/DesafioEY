package com.example.EY.application.put;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.TestUtils;
import com.example.EY.application.put.mapper.PutUsuarioMapper;
import com.example.EY.application.validator.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PutUsuarioGatewayTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidator usuarioValidator;

    @InjectMocks
    private PutUsuarioService putUsuarioService;

    @InjectMocks
    private PutUsuarioGateway putUsuarioGateway;

    @InjectMocks
    @Spy
    private PutUsuarioMapper putUsuarioMapper = new PutUsuarioMapper();

    private Usuario usuario;
    private UsuarioRequestDto requestDto;

    @BeforeEach
    void setUp() {
        putUsuarioService = new PutUsuarioService(usuarioRepository, usuarioValidator, putUsuarioMapper);
        putUsuarioGateway = new PutUsuarioGateway(putUsuarioService);

        usuario = TestUtils.getUsuario();

        requestDto = UsuarioRequestDto.builder()
                .nombre("Juan Actualizado")
                .correo("juan@dominio.cl")
                .contraseña("NewPass123")
                .build();
    }

    @Test
    void actualizarUsuario_Success() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        doNothing().when(usuarioValidator).validarUsuario(requestDto);

        UsuarioResponseDto result = putUsuarioGateway.actualizarUsuario(usuario.getId(), requestDto);

        verify(usuarioValidator).validarUsuario(requestDto);
        verify(usuarioRepository).save(usuario);

        assertNotNull(result);
        assertEquals(requestDto.getNombre(), usuario.getNombre());
        assertEquals(requestDto.getCorreo(), usuario.getCorreo());
        assertEquals(requestDto.getContraseña(), usuario.getContraseña());
    }

    @Test
    void actualizarUsuario_UsuarioNoEncontrado() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class,
                () -> putUsuarioGateway.actualizarUsuario(usuario.getId(), requestDto));
    }

}
