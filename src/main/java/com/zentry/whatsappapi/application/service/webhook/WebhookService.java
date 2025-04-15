package com.zentry.whatsappapi.application.service.webhook;

import com.zentry.whatsappapi.adapter.in.controller.webhook.dto.WebhookPayloadDTO;
import com.zentry.whatsappapi.adapter.in.websocket.MessageWebSocketController;
import com.zentry.whatsappapi.domain.model.ContactUpdate;
import com.zentry.whatsappapi.domain.model.MessageEvent;
import com.zentry.whatsappapi.infrastructure.Repository.ContactUpdateRepository;
import com.zentry.whatsappapi.infrastructure.Repository.MessageEventRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

@Service
public class WebhookService {

    private final MessageEventRepository messageEventRepository;
    private final ContactUpdateRepository contactUpdateRepository;
    private final MessageWebSocketController messageWebSocketController;

    public WebhookService(MessageEventRepository messageEventRepository, ContactUpdateRepository contactUpdateRepository, MessageWebSocketController messageWebSocketController) {
        this.messageEventRepository = messageEventRepository;
        this.contactUpdateRepository = contactUpdateRepository;
        this.messageWebSocketController = messageWebSocketController;
    }

    public void processWebhook(WebhookPayloadDTO payload) {
        System.out.println("Processando webhook no service...");
        System.out.println("Payload completo: " + payload);

        String eventType = payload.getEvent();
        System.out.println("Tipo de evento recebido: " + eventType);

        if ("contacts.update".equals(eventType)) {
            System.out.println("Evento contacts.update detectado.");
            Map<String, Object> data = payload.getData();
            if (data != null && data.containsKey("remoteJid") && data.containsKey("profilePicUrl") && data.containsKey("instanceId")) {
                ContactUpdate contactUpdate = new ContactUpdate();
                contactUpdate.setRemoteJid((String) data.get("remoteJid"));
                contactUpdate.setProfilePicUrl((String) data.get("profilePicUrl"));
                contactUpdate.setInstanceId((String) data.get("instanceId"));

                try {
                    contactUpdateRepository.save(contactUpdate);
                    System.out.println("Dados de contacts.update salvos: " + contactUpdate);
                } catch (DuplicateKeyException e) {
                    System.out.println("Contato já existe, atualizando...");
                    ContactUpdate existingContact = contactUpdateRepository.findByRemoteJid(contactUpdate.getRemoteJid());
                    existingContact.setProfilePicUrl(contactUpdate.getProfilePicUrl());
                    existingContact.setInstanceId(contactUpdate.getInstanceId());
                    contactUpdateRepository.save(existingContact);
                    System.out.println("Contato atualizado: " + existingContact);
                }
            } else {
                System.out.println("Dados incompletos para contacts.update: " + data);
            }
        }  else if ("messages.upsert".equals(eventType) || "send.message".equals(eventType)) {
            // Aqui tratamos todos os tipos de mensagem juntos
            System.out.println("Evento de mensagem detectado.");
            Map<String, Object> messageData = payload.getData();
            if (messageData != null && messageData.containsKey("key") && messageData.containsKey("message")) {
                MessageEvent messageToSave = new MessageEvent();
                messageToSave.setEvent(eventType);
                messageToSave.setInstance(payload.getInstance());

                // Definindo a direção da mensagem (IN ou OUT)
                String direction = "IN"; // Default é "IN"
                Map<String, Object> key = (Map<String, Object>) messageData.get("key");
                if (key != null) {
                    messageToSave.setRemoteJid((String) key.get("remoteJid"));
                    messageToSave.setFromMe((Boolean) key.get("fromMe"));
                    messageToSave.setMessageId((String) key.get("id"));
                    if (key.containsKey("participant")) {
                        messageToSave.setSender((String) key.get("participant"));
                    } else if (payload.getSender() != null) {
                        messageToSave.setSender(payload.getSender());
                    } else if (messageToSave.isFromMe()) {
                        messageToSave.setSender(messageToSave.getRemoteJid());
                    }else {
                        messageToSave.setSender(messageToSave.getRemoteJid());
                    }

                    // Definindo a direção da mensagem
                    if (messageToSave.isFromMe()) {
                        direction = "OUT"; // Se for enviado por nós, a direção é "OUT"
                    }
                }

                messageToSave.setPushName((String) messageData.get("pushName"));

                // Extraindo dados de 'message'
                Map<String, Object> actualMessage = (Map<String, Object>) messageData.get("message");
                if (actualMessage != null && actualMessage.containsKey("conversation")) {
                    messageToSave.setConversation((String) actualMessage.get("conversation"));
                    messageToSave.setMessageType("conversation");
                } else if (actualMessage != null && actualMessage.containsKey("imageMessage")) {
                    messageToSave.setMessageType("image");
                    // ... outros tipos de mensagem ...
                }

                Integer timestampInt = (Integer) messageData.get("messageTimestamp");
                if (timestampInt != null) {
                    messageToSave.setMessageTimestamp(timestampInt.longValue());
                    messageToSave.setDateTime(Instant.ofEpochSecond(timestampInt)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime());
                }

                System.out.println("Objeto MessageEvent ANTES de salvar: " + messageToSave);
                messageEventRepository.save(messageToSave);

                System.out.println("Objeto MessageEvent DEPOIS de salvar: " + messageToSave);
                messageWebSocketController.broadcastMessage(messageToSave);

                System.out.println("Mensagem SALVA (tentativa).");

            } else {
                System.out.println("Dados incompletos para a mensagem.");
            }
        } else {
            System.out.println("Evento não tratado: " + eventType);
        }
    }
}

