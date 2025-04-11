package com.zentry.whatsappapi.application.service.connection;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConnectionService {

    private final RestTemplate restTemplate;
    private final String API_URL = "http://localhost:8080//instance/create";
    private final String API_KEY = "12345";

    public ConnectionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createInstance(String number, Boolean qrcode, String instanceName, String integration, String webhookUrl, String chain) {
        // Monta o payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("number", number);
        payload.put("qrcode", qrcode);
        payload.put("instanceName", instanceName);
        payload.put("integration", integration);
        payload.put("webhookUrl", webhookUrl);
        payload.put("chain", chain
        );

        // Monta os headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            System.out.println("Resposta da API: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Erro ao criar instância: " + e.getMessage());
            throw new RuntimeException("Erro ao criar instância", e);
        }
    }

}
