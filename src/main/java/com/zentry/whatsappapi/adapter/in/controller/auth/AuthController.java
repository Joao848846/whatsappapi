package com.zentry.whatsappapi.adapter.in.controller.auth;

import com.zentry.whatsappapi.adapter.in.controller.auth.dto.JwtResponse;
import com.zentry.whatsappapi.application.service.auth.JwtService;
import com.zentry.whatsappapi.adapter.in.controller.auth.dto.JwtResponse;
import com.zentry.whatsappapi.adapter.in.controller.auth.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/token")
    public ResponseEntity<?> gerarToken(@RequestBody AuthRequest login) {
        // Validação das credenciais (pode ser substituída por validação com banco de dados)
        if (login.getUsername().equals("ADMIN") && login.getPassword().equals("ADMIN")) {
            // Gerar o token para o usuário
            JwtResponse jwtResponse = jwtService.gerarToken("1");  // Passe o ID de usuário correto aqui

            // Retorna o token e as informações de expiração
            return ResponseEntity.ok(jwtResponse);
        }

        // Se as credenciais estiverem erradas
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
