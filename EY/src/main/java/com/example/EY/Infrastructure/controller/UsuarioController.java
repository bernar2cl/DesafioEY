package com.example.EY.Infrastructure.controller;


import com.example.EY.Infrastructure.dto.ApiResponseDto;
import com.example.EY.Infrastructure.dto.UsuarioRequestDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseDto;
import com.example.EY.Infrastructure.dto.UsuarioResponseListDto;
import com.example.EY.application.del.DelUsuarioGateway;
import com.example.EY.application.get.GetUsuarioGateway;
import com.example.EY.application.patch.PatchUsuarioGateway;
import com.example.EY.application.post.PostUsuarioGateway;
import com.example.EY.application.put.PutUsuarioGateway;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final PostUsuarioGateway postUsuarioGateway;
    private final PutUsuarioGateway putUsuarioGateway;
    private final PatchUsuarioGateway patchUsuarioGateway;
    private final GetUsuarioGateway getUsuarioGateway;
    private final DelUsuarioGateway delUsuarioGateway;

    @PostMapping
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> crearUsuario(@Valid @RequestBody UsuarioRequestDto request) {
        try {
            UsuarioResponseDto response = postUsuarioGateway.crearUsuario(request);
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(response, null);
            return ResponseEntity.status(201).body(apiResponseDto);

        } catch (IllegalArgumentException e) {
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(null, e.getMessage());
            return ResponseEntity.badRequest().body(apiResponseDto);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UsuarioResponseListDto>> obtenerUsuario(@PathVariable UUID id) {
        return getUsuarioGateway.obtenerUsuario(id)
                .map(usuarioResponse -> ResponseEntity.ok(new ApiResponseDto<>(usuarioResponse, null)))  // Respuesta exitosa
                .orElse(ResponseEntity.status(404).body(new ApiResponseDto<>(null, "Usuario no encontrado")));  // Error
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<UsuarioResponseListDto>>> listarUsuarios() {
        List<UsuarioResponseListDto> usuarios = getUsuarioGateway.listarUsuarios();
        ApiResponseDto<List<UsuarioResponseListDto>> apiResponseDto = new ApiResponseDto<>(usuarios, null);
        return ResponseEntity.ok(apiResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> actualizarUsuario(@PathVariable UUID id,
                                                                                @Valid @RequestBody UsuarioRequestDto request) {
        try {
            UsuarioResponseDto usuarioResponseDto = putUsuarioGateway.actualizarUsuario(id, request);
            ApiResponseDto<UsuarioResponseDto> response = new ApiResponseDto<>(usuarioResponseDto, null);
            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            ApiResponseDto<UsuarioResponseDto> errorResponse = new ApiResponseDto<>(null, "Usuario no encontrado");
            return ResponseEntity.status(404).body(errorResponse);

        } catch (IllegalArgumentException e) {
            ApiResponseDto<UsuarioResponseDto> errorResponse = new ApiResponseDto<>(null, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> activarUsuario(@PathVariable UUID id) {
        try {
            UsuarioResponseDto response = patchUsuarioGateway.activarUsuario(id);
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(response, null);
            return ResponseEntity.ok(apiResponseDto);

        } catch (NoSuchElementException e) {
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(null, "Usuario no encontrado");
            return ResponseEntity.status(404).body(apiResponseDto);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(null, e.getMessage()));
        }

    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> desactivarUsuario(@PathVariable UUID id) {
        try {
            UsuarioResponseDto response = patchUsuarioGateway.desactivarUsuario(id);
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(response, null);
            return ResponseEntity.ok(apiResponseDto);

        } catch (NoSuchElementException e) {
            ApiResponseDto<UsuarioResponseDto> apiResponseDto = new ApiResponseDto<>(null, "Usuario no encontrado");
            return ResponseEntity.status(404).body(apiResponseDto);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(null, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> eliminarUsuario(@PathVariable UUID id) {
        try {
            delUsuarioGateway.eliminarUsuario(id);
            ApiResponseDto<Void> apiResponseDto = new ApiResponseDto<>(null, null);
            return ResponseEntity.noContent().build();

        } catch (NoSuchElementException e) {
            ApiResponseDto<Void> apiResponseDto = new ApiResponseDto<>(null, "Usuario no encontrado");
            return ResponseEntity.status(404).body(apiResponseDto);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(null, e.getMessage()));
        }
    }
}
