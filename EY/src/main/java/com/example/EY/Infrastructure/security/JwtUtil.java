package com.example.EY.Infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private static final String SECRET = "secreto";
    private static final long EXPIRATION = 1000 * 60;

    public static String generarToken(UUID userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .sign(Algorithm.HMAC256(SECRET));
    }
}
