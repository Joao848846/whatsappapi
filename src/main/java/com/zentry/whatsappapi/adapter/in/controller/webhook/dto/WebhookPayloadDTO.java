package com.zentry.whatsappapi.adapter.in.controller.webhook.dto;

import lombok.Data;
import java.util.Map;

@Data
public class WebhookPayloadDTO {

    private String event;
    private String instance;
    private String sender;
    private Object data; // Alterado para Object para aceitar Map ou List

    // Método para acessar os dados como um Map
    public Map<String, Object> getDataAsMap() {
        if (data instanceof Map) {
            return (Map<String, Object>) data;
        }
        return null;
    }

    // Método para acessar os dados como uma List (para contacts.update)
    public java.util.List<Map<String, Object>> getDataAsList() {
        if (data instanceof java.util.List) {
            return (java.util.List<Map<String, Object>>) data;
        }
        return null;
    }
}