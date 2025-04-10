package com.zentry.whatsappapi.adapter.in.controller.instance;

import com.zentry.whatsappapi.application.service.ConnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/create")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/instance")
    public  ResponseEntity<?> createInstance(@RequestBody Map<String, String> request) {

        String instanceName = request.get("instanceName");
        Boolean qrcode = Boolean.valueOf(request.get("qrcode"));
        String number = request.get("number");
        String integration = request.get("integration");
        String webhookUrl = request.get("webhookUrl");
        String chain = request.get("chain");

        System.out.println("Dados recebidos: number = " + number + ", text = " + qrcode);

        if (number == null || chain == null || webhookUrl == null || integration == null) {
            return ResponseEntity.badRequest().body("Número, cadeia, URL do webhook e integração são obrigatórios.");
        }

        try {
            String response = connectionService.createInstance(number, Boolean.valueOf(qrcode ? "true" : "false"), instanceName, integration, webhookUrl, chain);
            return ResponseEntity.ok().body(Map.of(
                    "message", "Instância criada com sucesso!",
                    "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
