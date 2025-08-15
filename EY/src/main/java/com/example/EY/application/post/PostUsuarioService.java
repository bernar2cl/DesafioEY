package com.example.EY.application.post;


import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.Infrastructure.security.JwtUtil;
import com.example.EY.application.post.mapper.PostUsuarioMapper;
import com.example.EY.application.validator.UsuarioValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;
    private final PostUsuarioMapper postUsuarioMapper;

    @Transactional
    public UsuarioResponseDto crearUsuario(UsuarioRequestDto request) {
        usuarioValidator.validarUsuario(request);

        Usuario usuario = postUsuarioMapper.toEntity(request, LocalDateTime.now());
        Usuario finalUsuario = usuario;
        usuario.getTelefonos().forEach(telefono -> telefono.setUsuario(finalUsuario));

        usuarioRepository.save(usuario);

        if(usuario.getToken()!=null){
            usuario.setToken(JwtUtil.generarToken(usuario.getId()));
            usuario = usuarioRepository.save(usuario);
        }

        return postUsuarioMapper.toResponse(usuario);
    }
}
