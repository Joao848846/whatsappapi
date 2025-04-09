package com.zentry.whatsappapi.controller;

import com.zentry.whatsappapi.service.WebhookService;
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
    public ResponseEntity<String> receiveWebhook(@RequestBody Map<String, Object> payload) {
        System.out.println("Payload recebido: " + payload); // 👈 Debug pra ver o que chega
        webhookService.processWebhook(payload.toString());
        return ResponseEntity.ok("Webhook recebido com sucesso!");
    }

    // Endpoint para verificação do webhook (GET)
    //@GetMapping
   // public ResponseEntity<String> verifyWebhook(@RequestParam(name = "hub.mode", required = false) String mode,
                                              //  @RequestParam(name = "hub.verify_token", required = false) String token,
                                                //RequestParam(name = "hub.challenge", required = false) String challenge) {
       // return webhookService.verifyWebhook(mode, token, challenge);
    //}
}
