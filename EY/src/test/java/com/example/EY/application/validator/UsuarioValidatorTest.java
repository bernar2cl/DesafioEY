package com.example.EY.application.validator;

import com.example.EY.Infrastructure.configuration.EmailProperties;
import com.example.EY.Infrastructure.configuration.PasswordProperties;
import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioValidatorTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioValidator validator;
    private EmailProperties emailProps;
    private PasswordProperties passwordProps;

    @BeforeEach
    void setUp() {
        emailProps = new EmailProperties();
        emailProps.setRegex("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

        passwordProps = new PasswordProperties();
        passwordProps.setRegex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d).+$");

        validator = new UsuarioValidator(usuarioRepository, passwordProps, emailProps);
    }

    @Test
    void validarUsuarioConDatosValidos() {
        UsuarioRequestDto request = UsuarioRequestDto.builder()
                .nombre("Juan Perez")
                .correo("juan.perez@dominio.cl")
                .contraseña("Ab12cd34")
                .build();

        when(usuarioRepository.findByCorreo(request.getCorreo())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validarUsuario(request));
    }

    @Test
    void validarUsuarioConCorreoDuplicado() {
        UsuarioRequestDto request = UsuarioRequestDto.builder()
                .nombre("Juan Perez")
                .correo("juan.perez@dominio.cl")
                .contraseña("Ab12cd34")
                .build();

        when(usuarioRepository.findByCorreo(request.getCorreo()))
                .thenReturn(Optional.of(mock(Usuario.class)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validarUsuario(request));

        assertEquals("El correo ya está registrado", exception.getMessage());
    }

    @Test
    void validarUsuarioConCorreoInvalido() {
        UsuarioRequestDto request = UsuarioRequestDto.builder()
                .nombre("Juan Perez")
                .correo("correo.invalido.com")
                .contraseña("Ab12cd34")
                .build();

        when(usuarioRepository.findByCorreo(request.getCorreo())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validarUsuario(request));

        assertEquals("Email no válido", exception.getMessage());
    }

    @Test
    void validarUsuarioConContraseñaInvalida() {
        UsuarioRequestDto request = UsuarioRequestDto.builder()
                .nombre("Juan Perez")
                .correo("juan.perez@dominio.cl")
                .contraseña("1234")
                .build();

        when(usuarioRepository.findByCorreo(request.getCorreo())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validarUsuario(request));

        assertEquals("Contraseña no válida", exception.getMessage());
    }
}