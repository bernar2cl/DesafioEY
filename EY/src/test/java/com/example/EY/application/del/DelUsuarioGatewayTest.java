package com.example.EY.application.del;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DelUsuarioGatewayTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DelUsuarioService delUsuarioService;

    @InjectMocks
    private DelUsuarioGateway delUsuarioGateway;

    private UUID usuarioId;
    private Usuario adminUsuario;
    private Usuario normalUsuario;

    @BeforeEach
    void setUp() {
        delUsuarioService = new DelUsuarioService(usuarioRepository);
        delUsuarioGateway = new DelUsuarioGateway(delUsuarioService);

        usuarioId = UUID.randomUUID();

        adminUsuario = new Usuario();
        adminUsuario.setId(UUID.randomUUID());
        adminUsuario.setRole("ADMIN");

        normalUsuario = new Usuario();
        normalUsuario.setId(UUID.randomUUID());
        normalUsuario.setRole("USER");
    }

    @Test
    void eliminarUsuario_Success_WhenNotLastAdmin() {
        when(usuarioRepository.findById(normalUsuario.getId())).thenReturn(Optional.of(normalUsuario));

        delUsuarioService.eliminarUsuario(normalUsuario.getId());

        verify(usuarioRepository, times(1)).delete(argThat(u -> u.getId().equals(normalUsuario.getId())));
    }

    @Test
    void eliminarUsuario_ThrowsException_WhenLastAdmin() {
        when(usuarioRepository.findAll()).thenReturn(List.of(adminUsuario));
        when(usuarioRepository.findById(adminUsuario.getId())).thenReturn(Optional.of(adminUsuario));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                delUsuarioService.eliminarUsuario(adminUsuario.getId())
        );

        assertEquals("No se puede eliminar el rol, ya que existe un solo ADMIN", exception.getMessage());
        verify(usuarioRepository, never()).delete(any());
    }

    @Test
    void eliminarUsuario_ThrowsEntityNotFoundException_WhenUsuarioNotFound() {
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                delUsuarioService.eliminarUsuario(usuarioId)
        );

        assertEquals("Usuario no encontrado con ID: " + usuarioId, exception.getMessage());
        verify(usuarioRepository, never()).delete(any());
    }
}
