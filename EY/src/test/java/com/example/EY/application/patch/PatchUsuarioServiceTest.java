package com.example.EY.application.patch;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.TestUtils;
import com.example.EY.application.patch.mapper.PatchUsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatchUsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    @Spy
    private PatchUsuarioMapper patchUsuarioMapper = new PatchUsuarioMapper();

    private PatchUsuarioService patchUsuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Sobrescribimos la instancia del service con los mocks ya inyectados
        patchUsuarioService = new PatchUsuarioService(usuarioRepository, patchUsuarioMapper);

        usuario = TestUtils.getUsuario();
        usuario.setId(UUID.randomUUID());
        usuario.setActivo(false);
        usuario.setRole("ADMIN");
    }

    @Test
    void activarUsuario_Success() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponseDto response = patchUsuarioService.activarUsuario(usuario.getId());

        verify(usuarioRepository).findById(usuario.getId());
        verify(usuarioRepository).save(usuario);

        assertTrue(usuario.isActivo());
        assertNotNull(usuario.getModificado());
        assertEquals(usuario.getId(), response.getId());
    }

    @Test
    void desactivarUsuario_Success() {
        // Simulamos que hay más de un admin
        Usuario otroAdmin = TestUtils.getUsuario();
        otroAdmin.setRole("ADMIN");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario, otroAdmin));
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponseDto response = patchUsuarioService.desactivarUsuario(usuario.getId());

        verify(usuarioRepository).findById(usuario.getId());
        verify(usuarioRepository).save(usuario);

        assertFalse(usuario.isActivo());
        assertNotNull(usuario.getModificado());
        assertEquals(usuario.getId(), response.getId());
    }

    @Test
    void desactivarUsuario_UnicoAdmin_LanzaExcepcion() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> patchUsuarioService.desactivarUsuario(usuario.getId()));

        assertEquals("No se puede desactivar el rol, ya que existe un solo ADMIN", exception.getMessage());
    }

    @Test
    void activarUsuario_UsuarioNoEncontrado_LanzaExcepcion() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class,
                () -> patchUsuarioService.activarUsuario(id));
    }
}