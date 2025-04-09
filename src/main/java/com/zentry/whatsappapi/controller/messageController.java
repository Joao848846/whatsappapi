package com.zentry.whatsappapi.controller;

import com.zentry.whatsappapi.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/message")
public class messageController {

    private final MessageService messageService;

    public messageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sendText")
    public ResponseEntity<?> sendTextMessage(@RequestBody Map<String, String> request) {
        String number = request.get("number");
        String text = request.get("text");

        System.out.println("Dados recebidos: number = " + number + ", text = " + text);

        if (number == null || text == null) {
            return ResponseEntity.badRequest().body("Número e texto são obrigatórios.");
        }

        try {
            String response = messageService.sendTextMessage(number, text);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Mensagem enviada com sucesso!",
                    "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
