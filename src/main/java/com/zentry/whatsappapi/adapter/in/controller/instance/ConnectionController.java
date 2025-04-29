package com.zentry.whatsappapi.adapter.in.controller.instance;

import com.zentry.whatsappapi.application.service.connection.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/create")
public class ConnectionController {

    private final ConnectionService connectionService;

    @Autowired
    private ObjectMapper objectMapper;


    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/instance")
    public  ResponseEntity<?> createInstance(@RequestBody Map<String, Object> request) {

        Map<String, Object> webhook = (Map<String, Object>) request.get("webhook");
        Boolean enabled = Boolean.valueOf((Boolean) webhook.get("enabled"));
        String url = (String) webhook.get("url");
        List<String> events = (List<String>) webhook.get("events");

        // Pega os dados do JSON
        String instanceName = (String) request.get("instanceName");
        Boolean qrcode = Boolean.valueOf((Boolean) request.get("qrcode"));
        String number = (String) request.get("number");
        String integration = (String) request.get("integration");
        String chain = (String) request.get("chain");

        System.out.println("Dados recebidos: number = " + number + ", text = " + qrcode);

        if (number == null || chain == null || webhook == null || integration == null) {
            return ResponseEntity.badRequest().body("Número, cadeia, URL do webhook e integração são obrigatórios.");
        }

        try {
            String response = connectionService.createInstance(number, Boolean.valueOf(qrcode ? "true" : "false"), instanceName, integration, chain, Boolean.valueOf(enabled ? "true" : "false"), url, events);
            Map<String, Object> responseMap = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {}); // Converte a string JSON para um mapa

            return ResponseEntity.ok().body(Map.of(
                    "message", "Instância criada com sucesso!",
                    "data", responseMap
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }

    }
}
