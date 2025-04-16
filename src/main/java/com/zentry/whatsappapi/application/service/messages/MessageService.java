package com.zentry.whatsappapi.application.service.messages;



import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {

    private static final String API_URL = "http://evolution-api:8080/";
    private static final String API_KEY = "12345";



    public Map<String, Object> sendTextMessage(String number, String text, String instanceId)
    {
        RestTemplate restTemplate = new RestTemplate();

        // Monta o payload
        Map<String, String> payload = new HashMap<>();
        payload.put("number", number);
        payload.put("text", text);

        // Monta os headers
        String url = API_URL + "message/sendText/" + instanceId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", API_KEY);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Resposta da API: " + response.getBody());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

            return responseMap;
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> reactionMessage(String instanceId, String remoteJid, String id, String reaction, Boolean fromMe) {

        RestTemplate restTemplate = new RestTemplate();

        // Mapa para o "key"
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("remoteJid", remoteJid);
        keyMap.put("fromMe", fromMe);
        keyMap.put("id", id);

        // Mapa principal (payload)
        Map<String, Object> payload = new HashMap<>();
        payload.put("key", keyMap);
        payload.put("reaction", reaction);

        // Headers
        String url = API_URL + "message/sendReaction/" + instanceId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Resposta da API: " + response.getBody());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            return responseMap;
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar mensagem", e);
        }
    }

    public Map<String, Object> sendMediaMessage(String instanceId, String number, String media, String caption, String mediatype, String mimetype, String fileName) {
        RestTemplate restTemplate = new RestTemplate();

        // Montando o payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("number", number);
        payload.put("media", media);
        payload.put("caption", caption);
        payload.put("mediatype", mediatype);
        payload.put("mimetype", mimetype);
        payload.put("fileName", fileName);


        // Headers
        String url = API_URL + "message/sendMedia/" + instanceId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Resposta da API: " + response.getBody());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            return responseMap;
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            throw new RuntimeException("Erro ao enviar mensagem", e);
        }
    }

}
