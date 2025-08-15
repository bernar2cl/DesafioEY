package com.example.EY.application.patch;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.application.patch.mapper.PatchUsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatchUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PatchUsuarioMapper patchUsuarioMapper;

    @Transactional
    public UsuarioResponseDto activarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setActivo(true);
        usuario.setModificado(LocalDateTime.now());
        usuarioRepository.save(usuario);
        return patchUsuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDto desactivarUsuario(UUID id) {
        long totalAdmins = usuarioRepository.findAll()
                .stream()
                .filter(f -> f.getRole().equalsIgnoreCase("ADMIN"))
                .count();

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        if (totalAdmins > 1) {
            usuario.setActivo(false);
            usuario.setModificado(LocalDateTime.now());
            usuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("No se puede desactivar el rol, ya que existe un solo ADMIN");
        }

        return patchUsuarioMapper.toResponse(usuario);
    }
}
