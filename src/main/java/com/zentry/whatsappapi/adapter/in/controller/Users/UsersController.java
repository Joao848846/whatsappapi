package com.zentry.whatsappapi.adapter.in.controller.Users;

import com.zentry.whatsappapi.adapter.in.controller.Users.dto.UsersDTO;
import com.zentry.whatsappapi.application.service.Users.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create")
    public ResponseEntity<UsersDTO> criarUsuario(@RequestBody @Valid UsersDTO usersDTO) {
        UsersDTO usuarioCriado = usersService.criarUsuario(usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping()
    public ResponseEntity<List<UsersDTO>> listarUsuarios() {
        List<UsersDTO> usuarios = usersService.ListarUsuarios().stream()
                .map(user -> new UsersDTO(user.getId(), user.getNome(), user.getNascimento(), user.getEmail(),
                        user.getTelefone(), user.getSenha(), user.getCpf(), user.getAtivo(),
                        user.getModos(), user.getTipo(), user.getUsername(), user.getEmpresa()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }
}