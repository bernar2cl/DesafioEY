package com.example.EY.application.get;


import com.example.EY.Infrastructure.db.entity.Usuario;
import com.example.EY.Infrastructure.db.repository.UsuarioRepository;
import com.example.EY.Infrastructure.dto.UsuarioResponseListDto;
import com.example.EY.application.get.mapper.GetUsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final GetUsuarioMapper getUsuarioMapper;

    public Optional<Usuario> obtenerCorreo(String correo){
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<UsuarioResponseListDto> obtenerUsuario(UUID id) {
        return usuarioRepository.findById(id)
                .map(getUsuarioMapper::toResponseList);
    }

    public List<UsuarioResponseListDto> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(getUsuarioMapper::toResponseList).toList();
    }

}
