package com.zentry.whatsappapi.application.service.auth;

import com.zentry.whatsappapi.adapter.in.controller.auth.dto.JwtResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Gerar token com idUsuario
    public JwtResponse gerarToken(String idUsuario) {
        // Data de criação e expiração do token
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + 86400000); // 24 horas (86400000ms)

        // Gerar o token
        String token = Jwts.builder()
                .setSubject(idUsuario)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();

        // Calcular o tempo de validade (em segundos)
        long expiresIn = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;

        // Retornar o token com o tempo de expiração
        return new JwtResponse(token, expiresIn, expirationDate);
    }

    // Validar token
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Obter os dados do token (claims)
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
