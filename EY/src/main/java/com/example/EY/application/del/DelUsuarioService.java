package com.example.EY.application.del;

import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DelUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void eliminarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));

        if (usuario.getRole().equalsIgnoreCase("ADMIN")) {
            long totalAdmins = usuarioRepository.findAll()
                    .stream()
                    .filter(f -> f.getRole().equalsIgnoreCase("ADMIN"))
                    .count();

            if (totalAdmins <= 1) {
                throw new IllegalArgumentException("No se puede eliminar el rol, ya que existe un solo ADMIN");
            }
        }

        usuarioRepository.delete(usuario);
    }
}
