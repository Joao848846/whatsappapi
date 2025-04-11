package com.zentry.whatsappapi.adapter.in.controller.message;

import com.zentry.whatsappapi.application.service.messages.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/message")
public class messageController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final MessageService messageService;


    public messageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sendText/{instanceId}")
    public ResponseEntity<?> sendTextMessage(@RequestBody Map<String, String> request, @PathVariable String instanceId) {

        String number = request.get("number");
        String text = request.get("text");

        System.out.println("Dados recebidos: number = " + number + ", text = " + text);

        if (number == null || text == null) {
            return ResponseEntity.badRequest().body("Número e texto são obrigatórios.");
        }

        try {
            Map<String, Object> response = messageService.sendTextMessage(number, text , instanceId);
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

    @PostMapping("/reaction/{instanceId}")
    public ResponseEntity<?> reactionMessage(@RequestBody Map<String, Object> request, @PathVariable String instanceId) {
        // Primeiro, pega o objeto 'key' do JSON
        Map<String, Object> key = (Map<String, Object>) request.get("key");

        // Agora, pega os valores de dentro de 'key'
        String remoteJid = (String) key.get("remoteJid");
        String id = (String) key.get("id");
        Boolean fromMe = (Boolean) key.get("fromMe");

        // Pega o campo 'reaction' que está fora de 'key'
        String reaction = (String) request.get("reaction");

        System.out.println("Dados recebidos: remoteJid = " + remoteJid + ", id = " + id + ", reaction = " + reaction + ", fromMe = " + fromMe);

        if (remoteJid == null || id == null || reaction == null || fromMe == null) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios.");
        }

        try {
            Map<String, Object> response = messageService.reactionMessage(instanceId, remoteJid, id, reaction, fromMe);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Reação enviada com sucesso!",
                    "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/sendMedia/{instanceId}")
    public ResponseEntity<?> sendMediaMessage(@RequestBody Map<String, String> request, @PathVariable String instanceId) {
        String number = (String) request.get("number");
        String media = (String) request.get("media");
        String caption = (String) request.get("caption");
        String mediatype = (String) request.get("mediatype");
        String fileName = (String) request.get("fileName");
        String mimetype = (String) request.get("mimetype");

        System.out.println("Dados recebidos: number = " + number + ", mediaUrl = " + media + ", caption = " + caption);

        if (number == null || media == null) {
            return ResponseEntity.badRequest().body("Número e URL do mídia são obrigatórios.");
        }

        try {
            Map<String, Object> response = messageService.sendMediaMessage(instanceId, number, media, caption, mediatype, mimetype, fileName);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Mensagem de mídia enviada com sucesso!",
                    "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

}
