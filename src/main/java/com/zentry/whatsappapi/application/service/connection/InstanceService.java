package com.zentry.whatsappapi.application.service.connection;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.*;

import java.util.Base64;
import java.util.Map;

@Service
public class InstanceService {

    private final RestTemplate restTemplate;
    private final String API_URL = "http://evolution-api:8080/";
    private final String API_KEY = "12345";

    public InstanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public  String fetchInstances() { // Connection State
        String url = API_URL + "instance/fetchInstances";
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println(response.getBody());

        return response.getBody();


    }

    public Map<String, Object> fetchInstanceStatus(String instanceId) {  // Connection State
        String url = API_URL + "/instance/connect/" + instanceId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println(response.getBody());

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            return responseMap;
        } catch (Exception e) {
            System.err.println("Erro ao buscar status: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar status", e);
        }


    }

    public Map<String, Object> deleteInstance(String instanceId) {  // Connection State
        String url = API_URL + "/instance/delete/" + instanceId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        System.out.println(response.getBody());

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            return  responseMap;
        } catch (Exception e) {
            System.err.println("Erro ao buscar status: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar status", e);
        }
    }

    public byte[] GetQrcode(String instanceId) {
        String url = API_URL + "/instance/connect/" + instanceId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> responseMap = mapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

                if (responseMap.containsKey("base64") && responseMap.get("base64") instanceof String) {
                    String base64StringWithPrefix = (String) responseMap.get("base64");
                    if (base64StringWithPrefix.contains(",")) {
                        base64StringWithPrefix = base64StringWithPrefix.substring(base64StringWithPrefix.indexOf(',') + 1);
                    }
                    return Base64.getDecoder().decode(base64StringWithPrefix);
                } else {
                    throw new RuntimeException("Campo 'base64' não encontrado ou não é uma string na resposta do QR Code.");
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar resposta do QR Code: " + e.getMessage());
                throw new RuntimeException("Erro ao processar resposta do QR Code", e);
            }
        } else {
            throw new RuntimeException("Falha na chamada à API externa. Status: " + response.getStatusCode());
        }
    }
}
