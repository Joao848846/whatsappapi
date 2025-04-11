package com.zentry.whatsappapi.adapter.in.controller.webhook;

import com.zentry.whatsappapi.adapter.in.controller.webhook.dto.WebhookPayloadDTO;
import com.zentry.whatsappapi.application.service.webhook.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    // Endpoint para receber as notificações do webhook (POST)
    @PostMapping
    public ResponseEntity<String> receiveWebhook(@RequestBody WebhookPayloadDTO payload) {
        System.out.println("Payload recebido: " + payload); // 👈 Debug pra ver o que chega
        webhookService.processWebhook(payload);
        return ResponseEntity.ok("Webhook recebido com sucesso!");
    }

}
