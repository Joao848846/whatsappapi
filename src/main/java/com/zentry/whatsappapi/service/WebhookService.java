package com.zentry.whatsappapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    public void processWebhook(String payload) {
        // Aqui você trata o que vem no webhook
        System.out.println("Processando webhook no service...");
        System.out.println(payload);

        // Aqui você vai depois:
        // - Fazer o parser do JSON
        // - Salvar no banco
        // - Enviar para o WebSocket
        // - Ou qualquer outra ação
    }
}
