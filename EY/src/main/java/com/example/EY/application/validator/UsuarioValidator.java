package com.example.EY.application.validator;

import com.example.EY.Infrastructure.configuration.EmailProperties;
import com.example.EY.Infrastructure.configuration.PasswordProperties;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;
    private final PasswordProperties passwordProperties;
    private final EmailProperties emailProperties;

    public void validarUsuario(UsuarioRequestDto request) {
        validarCorreoUnico(request.getCorreo());
        validarFormatoCorreo(request.getCorreo());
        validarFormatoContraseña(request.getContraseña());
    }

    private void validarCorreoUnico(String correo) {
        usuarioRepository.findByCorreo(correo).ifPresent(u -> {
            throw new IllegalArgumentException("El correo ya está registrado");
        });
    }

    private void validarFormatoCorreo(String correo) {
        if (!correo.matches(emailProperties.getRegex())) {
            throw new IllegalArgumentException("Email no válido");
        }
    }

    private void validarFormatoContraseña(String contraseña) {
        if (!contraseña.matches(passwordProperties.getRegex())) {
            throw new IllegalArgumentException("Contraseña no válida");
        }
    }
}
