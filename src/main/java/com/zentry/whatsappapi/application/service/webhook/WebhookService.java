package com.zentry.whatsappapi.application.service.webhook;

import com.zentry.whatsappapi.adapter.in.controller.webhook.dto.WebhookPayloadDTO;
import com.zentry.whatsappapi.domain.model.ContactUpdate;
import com.zentry.whatsappapi.domain.model.Messages;
import com.zentry.whatsappapi.domain.model.SendMessages;
import com.zentry.whatsappapi.infrastructure.Repository.ContactUpdateRepository;
import com.zentry.whatsappapi.infrastructure.Repository.MessagesRepository;
import com.zentry.whatsappapi.infrastructure.Repository.SendMessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Service
public class WebhookService {

    private final MessagesRepository messagesRepository;
    private final ContactUpdateRepository contactUpdateRepository;
    private final SendMessageRepository sendMessagesRepository;

    public WebhookService(MessagesRepository messagesRepository, ContactUpdateRepository contactUpdateRepository, SendMessageRepository sendMessagesRepository) {
        this.messagesRepository = messagesRepository;
        this.contactUpdateRepository = contactUpdateRepository;
        this.sendMessagesRepository = sendMessagesRepository;
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
                    // ... outros tipos de mensagem ...
                }
                // ... outros tipos de mensagem ...

                Integer timestampInt = (Integer) messageData.get("messageTimestamp");
                if (timestampInt != null) {
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
        } else if ("contacts.update".equals(eventType)) {
            System.out.println("Evento contacts.update detectado.");
            Map<String, Object> data = payload.getData();
            if (data != null && data.containsKey("remoteJid") && data.containsKey("profilePicUrl") && data.containsKey("instanceId")) {
                ContactUpdate contactUpdate = new ContactUpdate();
                contactUpdate.setRemoteJid((String) data.get("remoteJid"));
                contactUpdate.setProfilePicUrl((String) data.get("profilePicUrl"));
                contactUpdate.setInstanceId((String) data.get("instanceId"));

                contactUpdateRepository.save(contactUpdate);
                System.out.println("Dados de contacts.update salvos: " + contactUpdate);
            } else {
                System.out.println("Dados incompletos para contacts.update: " + data);
            }
        } else if ("send.message".equals(eventType)) {
            System.out.println("Evento send.message detectado.");
            Map<String, Object> data = payload.getData();
            System.out.println("Dados do payload.getData() como Map: " + data);
            if (data != null && data.containsKey("key") && data.containsKey("message")) {
                SendMessages sendMessagesTosave = new SendMessages();
                sendMessagesTosave.setEvent(eventType);
                sendMessagesTosave.setInstance(payload.getInstance());

                // Extraindo dados de 'key'
                Map<String, Object> key = (Map<String, Object>) data.get("key");
                System.out.println("Dados da 'key': " + key);
                if (key != null) {
                    if (key.containsKey("id")) {
                        sendMessagesTosave.setMessageId((String) key.get("id"));
                    }
                    if (key.containsKey("remoteJid")) {
                        sendMessagesTosave.setRemoteJid((String) key.get("remoteJid"));
                    }
                }

                // Extraindo outros dados diretamente de 'data'
                if (data.containsKey("instanceId")) {
                    sendMessagesTosave.setInstance((String) data.get("instanceId"));
                }

                // Extraindo dados de 'message'
                Map<String, Object> actualMessage = (Map<String, Object>) data.get("message");
                if (actualMessage != null && actualMessage.containsKey("conversation")) {
                    sendMessagesTosave.setConversation((String) actualMessage.get("conversation"));
                    sendMessagesTosave.setMessageType("conversation");
                }

                System.out.println("Objeto SendMessages ANTES de salvar: " + sendMessagesTosave);
                sendMessagesRepository.save(sendMessagesTosave);

            } else {
                System.out.println("Dados incompletos para send.message (faltando 'key' ou 'message').");
            }
        } else {
            System.out.println("Evento não tratado: " + eventType);
        }
    }
}