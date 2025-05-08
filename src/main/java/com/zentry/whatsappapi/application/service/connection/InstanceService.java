package com.zentry.whatsappapi.application.service.connection;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.*;

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
}
