package com.zentry.whatsappapi.application.service.webhook;

import com.zentry.whatsappapi.adapter.in.controller.webhook.dto.WebhookPayloadDTO;
import com.zentry.whatsappapi.domain.model.Messages;
import com.zentry.whatsappapi.infrastructure.Repository.MessagesRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class WebhookService {

    private final MessagesRepository messagesRepository;

    // Injetando o MessagesRepository diretamente aqui
    public WebhookService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public void processWebhook(WebhookPayloadDTO payload) {
        System.out.println("Processando webhook no service...");
        System.out.println("Payload completo: " + payload);

        String eventType = payload.getEvent();
        System.out.println("Tipo de evento recebido: " + eventType);

        if ("messages.upsert".equals(eventType)) {
            System.out.println("Evento de nova mensagem detectado.");
            Map<String, Object> messageData = payload.getData();
            System.out.println("Dados do payload.getData() como Map: " + messageData);
            if (messageData != null && messageData.containsKey("key") && messageData.containsKey("message")) {
                Messages messageToSave = new Messages();
                messageToSave.setEvent(eventType);
                messageToSave.setInstance(payload.getInstance());

                // Extraindo dados de 'key'
                Map<String, Object> key = (Map<String, Object>) messageData.get("key");
                System.out.println("Dados da 'key': " + key);
                if (key != null) {
                    messageToSave.setRemoteJid((String) key.get("remoteJid"));
                    messageToSave.setFromMe((Boolean) key.get("fromMe"));
                    messageToSave.setMessageId((String) key.get("id"));
                    if (key.containsKey("participant")) {
                        messageToSave.setSender((String) key.get("participant"));
                    } else {
                        messageToSave.setSender(messageToSave.getRemoteJid());
                    }
                }

                messageToSave.setPushName((String) messageData.get("pushName"));
                System.out.println("Push Name extraído: " + messageToSave.getPushName());

                // Extraindo dados de 'message'
                Map<String, Object> actualMessage = (Map<String, Object>) messageData.get("message");
                System.out.println("Dados de 'message': " + actualMessage);
                if (actualMessage != null && actualMessage.containsKey("conversation")) {
                    messageToSave.setConversation((String) actualMessage.get("conversation"));
                    messageToSave.setMessageType("conversation");
                } else if (actualMessage != null && actualMessage.containsKey("imageMessage")) {
                    messageToSave.setMessageType("image");
                    // ...
                }
                // ... outros tipos de mensagem ...

                Integer timestampInt = (Integer) messageData.get("messageTimestamp");
                if (timestampInt  != null) {
                    messageToSave.setMessageTimestamp(timestampInt.longValue());
                    messageToSave.setDateTime(Instant.ofEpochSecond(timestampInt)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime());
                }

                System.out.println("Objeto Messages ANTES de salvar: " + messageToSave);
                messagesRepository.save(messageToSave);
                System.out.println("Mensagem SALVA (tentativa).");

            } else {
                System.out.println("As chaves 'key' ou 'message' não foram encontradas em payload.getData().");
            }
        } else {
            System.out.println("Evento não tratado: " + eventType);
        }
    }
}