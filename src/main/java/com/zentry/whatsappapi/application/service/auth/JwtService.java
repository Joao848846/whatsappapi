package com.zentry.whatsappapi.application.service.auth;

import com.zentry.whatsappapi.adapter.in.controller.auth.dto.JwtResponse;
import com.zentry.whatsappapi.domain.model.users.Users; // Importar a entidade Users
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap; // Importar
import java.util.Map;     // Importar

@Service
public class JwtService {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // --- MÉTODO MODIFICADO ---
    // Agora ele recebe o objeto Users completo para ter acesso a todos os dados
    public JwtResponse gerarToken(Users usuario) {
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + 86400000); // 24 horas

        // Criamos um mapa de "claims" para adicionar informações extras ao token
        Map<String, Object> claims = new HashMap<>();
        claims.put("empresaId", usuario.getEmpresa());
        claims.put("username", usuario.getUsername());
        // No futuro, você poderia adicionar as permissões (modos) do usuário aqui também

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getId()) // O "dono" (subject) do token continua sendo o ID do usuário
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();

        long expiresIn = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;

        return new JwtResponse(token, expiresIn, expirationDate);
    }

    // Os métodos validarToken e getClaims continuam iguais
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

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}