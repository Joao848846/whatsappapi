package com.zentry.whatsappapi.infrastructure.config.filter; // Pacote sugerido

import com.zentry.whatsappapi.application.service.auth.CustomUserDetailsService;
import com.zentry.whatsappapi.application.service.auth.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marca esta classe como um componente Spring para podermos injetá-la
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Se não há token no cabeçalho, passa para o próximo filtro sem fazer nada.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrai o token do cabeçalho (removendo o "Bearer ")
        jwt = authHeader.substring(7);

        // 3. Extrai o username de dentro do token
        username = jwtService.getClaims(jwt).get("username", String.class);

        // 4. Se temos o username e o utilizador ainda não foi autenticado para esta requisição...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carrega os detalhes do usuário do banco
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            // 5. Se o token for válido...
            if (jwtService.validarToken(jwt)) {
                // Cria um objeto de autenticação que o Spring Security entende
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Não precisamos da senha aqui
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Coloca o utilizador como "autenticado" para o resto desta requisição.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Passa a requisição adiante na cadeia de filtros do Spring
        filterChain.doFilter(request, response);
    }
}