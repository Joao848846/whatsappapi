package com.zentry.whatsappapi.adapter.in.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String token;      // O token gerado
    private long expiresIn;    // Tempo de validade restante em segundos
    private Date expiresAt;    // Data de expiração
}