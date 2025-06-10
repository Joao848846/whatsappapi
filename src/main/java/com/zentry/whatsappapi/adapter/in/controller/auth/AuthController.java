package com.zentry.whatsappapi.adapter.in.controller.auth;

import com.zentry.whatsappapi.adapter.in.controller.auth.dto.AuthRequest;
import com.zentry.whatsappapi.adapter.in.controller.auth.dto.JwtResponse;
import com.zentry.whatsappapi.application.service.auth.JwtService;
import com.zentry.whatsappapi.domain.model.users.Users;
import com.zentry.whatsappapi.infrastructure.Repository.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;

    // Ajustamos o construtor para injetar todas as dependências necessárias
    public AuthController(JwtService jwtService,
                          AuthenticationManager authenticationManager,
                          UsersRepository usersRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/token")
    public ResponseEntity<?> gerarToken(@RequestBody AuthRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Se a autenticação foi bem-sucedida, o código continua.
            if (authentication.isAuthenticated()) {
                Users usuario = usersRepository.findByUsername(loginRequest.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado após autenticação."));

                // Geramos o ‘token’ passando o objeto ‘Users’ completo.
                JwtResponse jwtResponse = jwtService.gerarToken(usuario);
                return ResponseEntity.ok(jwtResponse);
            } else {
                return ResponseEntity.status(401).body("Autenticação falhou.");
            }
        } catch (Exception e) {
            // Se o authenticationManager.authenticate() falhar (senha errada, etc), ele lança uma exceção.
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        }
    }
}