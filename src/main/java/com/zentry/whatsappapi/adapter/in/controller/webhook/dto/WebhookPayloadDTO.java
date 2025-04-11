package com.zentry.whatsappapi.adapter.in.controller.webhook.dto;

import lombok.Data;
import java.util.Map;

@Data
public class WebhookPayloadDTO {

    private String event;
    private String instance;
    private Map<String, Object> data;



}
