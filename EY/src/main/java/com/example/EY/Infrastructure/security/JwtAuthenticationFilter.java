package com.example.EY.Infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.EY.Infrastructure.configuration.exception.UnauthorizedException;
import com.example.EY.Infrastructure.dto.UsuarioResponseListDto;
import com.example.EY.application.get.GetUsuarioGateway;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class JwtAuthenticationFilter implements Filter {

    private static final String SECRET_KEY = "secreto";  // Clave secreta
    private final GetUsuarioGateway getUsuarioGateway;

    public JwtAuthenticationFilter(GetUsuarioGateway getUsuarioGateway) {
        this.getUsuarioGateway = getUsuarioGateway;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        // Rutas públicas
        if (path.startsWith("/auth/login") || path.startsWith("/swagger") || path.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(httpRequest);

        try {
            if (token == null) {
                throw new UnauthorizedException("Token no proporcionado");
            }

            UUID userId = UUID.fromString(extractUsernameFromToken(token));
            Optional<UsuarioResponseListDto> usuarioOpt = getUsuarioGateway.obtenerUsuario(userId);

            if (usuarioOpt.isEmpty()) {
                throw new UnauthorizedException("Usuario no encontrado");
            }

            String role = usuarioOpt.get().getRole().toUpperCase();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userId, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);

        } catch (JWTVerificationException e) {
            sendUnauthorized(httpResponse, "Sesión expirada o no autorizado");
        } catch (UnauthorizedException e) {
            sendUnauthorized(httpResponse, e.getMessage());
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private String extractUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }

    private void sendUnauthorized(HttpServletResponse response, String mensaje) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"data\":null,\"mensaje\":\"" + mensaje + "\"}");
    }
}