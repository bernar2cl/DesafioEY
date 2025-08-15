package com.example.EY.application.put;


import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.application.put.mapper.PutUsuarioMapper;
import com.example.EY.application.validator.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PutUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;
    private final PutUsuarioMapper putUsuarioMapper;

    @Transactional
    public UsuarioResponseDto actualizarUsuario(UUID id, UsuarioRequestDto request) {
        usuarioValidator.validarUsuario(request);

        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        putUsuarioMapper.actualizarUsuarioDesdeRequest(usuario, request);
        usuario.setModificado(LocalDateTime.now());

        usuarioRepository.save(usuario);
        return putUsuarioMapper.toResponse(usuario);
    }
    @Transactional
    public UsuarioResponseDto actualizarToken(Usuario usuario) {
        usuarioRepository.save(usuario);
        return putUsuarioMapper.toResponse(usuario);
    }
}
