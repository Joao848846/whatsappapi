package com.zentry.whatsappapi.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {

    private static final String API_URL = "http://localhost:8080/message/sendText/Joao";
    private static final String API_KEY = "12345";



    public String sendTextMessage(String number, String text) {
        RestTemplate restTemplate = new RestTemplate();

        // Monta o payload
        Map<String, String> payload = new HashMap<>();
        payload.put("number", number);
        payload.put("text", text);

        // Monta os headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", API_KEY);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
            System.out.println("Resposta da API: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar mensagem", e);
        }
    }
}
