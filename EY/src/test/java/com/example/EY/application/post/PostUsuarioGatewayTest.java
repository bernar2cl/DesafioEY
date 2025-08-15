package com.example.EY.application.post;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.application.post.mapper.PostUsuarioMapper;
import com.example.EY.application.validator.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostUsuarioGatewayTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidator usuarioValidator;

    @InjectMocks
    @Spy
    private PostUsuarioMapper postUsuarioMapper = new PostUsuarioMapper();

    @InjectMocks
    private PostUsuarioService postUsuarioService;

    @InjectMocks
    private PostUsuarioGateway postUsuarioGateway; // Mockito inyecta postUsuarioService automáticamente

    private UsuarioRequestDto requestDto;

    @BeforeEach
    void setUp() {
        postUsuarioGateway = new PostUsuarioGateway(postUsuarioService);
        postUsuarioService = new PostUsuarioService(usuarioRepository, usuarioValidator, postUsuarioMapper);

        requestDto = UsuarioRequestDto.builder()
                .nombre("Juan Actualizado")
                .correo("juan12@dominio.cl")
                .contraseña("NewPass123")
                .build();
    }

    @Test
    void crearUsuario_Success() {
        doNothing().when(usuarioValidator).validarUsuario(requestDto);

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponseDto result = postUsuarioGateway.crearUsuario(requestDto);

        verify(usuarioValidator).validarUsuario(requestDto);
        verify(usuarioRepository).save(any(Usuario.class));

        assertNotNull(result);
        assertNotNull(result.getCreado());
    }
}