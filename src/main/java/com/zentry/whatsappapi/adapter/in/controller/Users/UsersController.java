package com.zentry.whatsappapi.adapter.in.controller.Users;

import com.zentry.whatsappapi.adapter.in.controller.Users.dto.UsersDTO;
import com.zentry.whatsappapi.application.service.Users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create")
    public ResponseEntity<UsersDTO> criarUsuario(@RequestBody UsersDTO usersDTO) {
        UsersDTO usuarioCriado = usersService.criarUsuario(usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
}