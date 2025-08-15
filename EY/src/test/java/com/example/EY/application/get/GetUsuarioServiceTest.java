package com.example.EY.application.get;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioResponseListDto;
import com.example.EY.TestUtils;
import com.example.EY.application.get.mapper.GetUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    @Spy
    private GetUsuarioMapper getUsuarioMapper = new GetUsuarioMapper();

    @InjectMocks
    private GetUsuarioService getUsuarioService;

    private GetUsuarioGateway getUsuarioGateway;

    private Usuario usuario;
    private UsuarioResponseListDto usuarioResponse;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        getUsuarioService = new GetUsuarioService(usuarioRepository, getUsuarioMapper);
        getUsuarioGateway = new GetUsuarioGateway(getUsuarioService);

        usuario = TestUtils.getUsuario();
        usuarioId = usuario.getId();

        usuarioResponse = UsuarioResponseListDto.builder()
                .id(usuarioId)
                .correo(usuario.getCorreo())
                .build();
    }

    @Test
    void testObtenerCorreo_Existe() {
        when(usuarioRepository.findByCorreo("juan@dominio.cl")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = getUsuarioService.obtenerCorreo("juan@dominio.cl");

        assertTrue(resultado.isPresent());
        assertEquals("juan@dominio.cl", resultado.get().getCorreo());
        verify(usuarioRepository, times(1)).findByCorreo("juan@dominio.cl");
    }

    @Test
    void testObtenerCorreo_NoExiste() {
        when(usuarioRepository.findByCorreo("notfound@example.com")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = getUsuarioService.obtenerCorreo("notfound@example.com");

        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByCorreo("notfound@example.com");
    }

    @Test
    void testObtenerUsuario_Existe() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(getUsuarioMapper.toResponseList(usuario)).thenReturn(usuarioResponse);

        Optional<UsuarioResponseListDto> resultado = getUsuarioService.obtenerUsuario(usuarioId);

        assertTrue(resultado.isPresent());
        assertEquals(usuarioId, resultado.get().getId());
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(getUsuarioMapper, times(1)).toResponseList(usuario);
    }

    @Test
    void testObtenerUsuario_NoExiste() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        Optional<UsuarioResponseListDto> resultado = getUsuarioService.obtenerUsuario(usuarioId);

        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verifyNoInteractions(getUsuarioMapper);
    }

    @Test
    void testListarUsuarios() {
        List<Usuario> usuarios = List.of(usuario);
        List<UsuarioResponseListDto> usuariosResponse = List.of(usuarioResponse);

        when(usuarioRepository.findAll()).thenReturn(usuarios);
        when(getUsuarioMapper.toResponseList(usuario)).thenReturn(usuarioResponse);

        List<UsuarioResponseListDto> resultado = getUsuarioService.listarUsuarios();

        assertEquals(1, resultado.size());
        assertEquals(usuarioId, resultado.get(0).getId());
        verify(usuarioRepository, times(1)).findAll();
        verify(getUsuarioMapper, times(1)).toResponseList(usuario);
    }
}
