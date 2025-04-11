package com.zentry.whatsappapi.application.service.types;

import lombok.Data;

@Data
public class MessageType {
    private ApiResponse data;
    private String message;

    @Data
    public static class ApiResponse {
        private Key key;
        private String pushName;
        private Message message;
        private Object contextInfo;
        private String messageType;
        private long messageTimestamp;
        private String instanceId;
        private String source;
    }

    @Data
    public static class Key {
        private String remoteJid;
        private boolean fromMe;
        private String id;
    }

    @Data
    public static class Message {
        private String conversation;
    }
}
