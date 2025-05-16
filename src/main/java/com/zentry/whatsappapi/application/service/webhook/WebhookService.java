package com.zentry.whatsappapi.application.service.webhook;

import com.zentry.whatsappapi.adapter.in.controller.webhook.dto.WebhookPayloadDTO;
import com.zentry.whatsappapi.adapter.in.websocket.MessageWebSocketController;
import com.zentry.whatsappapi.domain.model.ContactUpdate;
import com.zentry.whatsappapi.domain.model.MessageEvent;
import com.zentry.whatsappapi.domain.model.scheduling.MensagemNaFila;
import com.zentry.whatsappapi.domain.model.scheduling.scheduling;
import com.zentry.whatsappapi.infrastructure.Repository.ContactUpdateRepository;
import com.zentry.whatsappapi.infrastructure.Repository.MessageEventRepository;
import com.zentry.whatsappapi.infrastructure.Repository.schedulingRepository;
import com.zentry.whatsappapi.infrastructure.Repository.MensagemNaFilaRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class WebhookService {

    private final MessageEventRepository messageEventRepository;
    private final ContactUpdateRepository contactUpdateRepository;
    private final MessageWebSocketController messageWebSocketController;
    private final schedulingRepository schedulingRepository;
    private final MensagemNaFilaRepository mensagemNaFilaRepository;

    public WebhookService(MessageEventRepository messageEventRepository, ContactUpdateRepository contactUpdateRepository, MessageWebSocketController messageWebSocketController, schedulingRepository schedulingRepository, MensagemNaFilaRepository mensagemNaFilaRepository) {
        this.messageEventRepository = messageEventRepository;
        this.contactUpdateRepository = contactUpdateRepository;
        this.messageWebSocketController = messageWebSocketController;
        this.schedulingRepository = schedulingRepository;
        this.mensagemNaFilaRepository = mensagemNaFilaRepository;
    }

    public void processWebhook(WebhookPayloadDTO payload) {
        System.out.println("Processando webhook no service...");
        System.out.println("Payload completo: " + payload);

        String eventType = payload.getEvent();
        System.out.println("Tipo de evento recebido: " + eventType);

        if ("contacts.update".equals(eventType)) {
            System.out.println("Evento contacts.update detectado.");
            java.util.List<Map<String, Object>> dataList = payload.getDataAsList();
            if (dataList != null && !dataList.isEmpty()) {
                Map<String, Object> data = dataList.get(0); // Pegamos o primeiro objeto da lista
                if (data.containsKey("remoteJid") && data.containsKey("profilePicUrl") && data.containsKey("instanceId")) {
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
            } else {
                System.out.println("Lista de dados vazia para contacts.update.");
            }
        } else if ("messages.upsert".equals(eventType) || "send.message".equals(eventType)) {
            System.out.println("Evento de mensagem detectado.");
            Map<String, Object> messageData = payload.getDataAsMap();
            if (messageData != null && messageData.containsKey("key") && messageData.containsKey("message")) {
                MessageEvent messageToSave = new MessageEvent();
                messageToSave.setEvent(eventType);
                messageToSave.setInstance(payload.getInstance());

                Map<String, Object> key = (Map<String, Object>) messageData.get("key");
                String remoteJid = null;
                if (key != null) {
                    remoteJid = (String) key.get("remoteJid");
                    messageToSave.setRemoteJid(remoteJid);
                    messageToSave.setFromMe((Boolean) key.get("fromMe"));
                    messageToSave.setMessageId((String) key.get("id"));
                    if (key.containsKey("participant")) {
                        messageToSave.setSender((String) key.get("participant"));
                    } else if (payload.getSender() != null) {
                        messageToSave.setSender(payload.getSender());
                    } else if (messageToSave.isFromMe()) {
                        messageToSave.setSender(messageToSave.getRemoteJid());
                    } else {
                        messageToSave.setSender(messageToSave.getRemoteJid());
                    }
                }

                messageToSave.setPushName((String) messageData.get("pushName"));

                Map<String, Object> actualMessage = (Map<String, Object>) messageData.get("message");
                if (actualMessage != null && actualMessage.containsKey("conversation")) {
                    messageToSave.setConversation((String) actualMessage.get("conversation"));
                    messageToSave.setMessageType("conversation");
                } else if (actualMessage != null && actualMessage.containsKey("imageMessage")) {
                    messageToSave.setMessageType("image");
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

                // Lógica para NÃO atualizar o Scheduling para o evento 'send.message'
                if ("send.message".equals(eventType) && remoteJid != null) {
                    String telefone = remoteJid.replace("@s.whatsapp.net", "");
                    System.out.println("Buscando agendamento para o telefone (send.message): " + telefone);
                    scheduling agendamento = schedulingRepository.findByTelefone(telefone);

                    if (agendamento != null) {
                        System.out.println("Agendamento ENCONTRADO (send.message - ID): " + agendamento.getId());
                        // *** NÃO ATUALIZAR O SCHEDULING AQUI ***
                        System.out.println("Status do lembrete NÃO atualizado no send.message para o telefone: " + telefone);
                    } else {
                        System.out.println("Agendamento NÃO ENCONTRADO para o telefone (send.message): " + telefone);
                    }
                }
            } else {
                System.out.println("Dados incompletos para a mensagem.");
            }
        } else if ("messages.update".equals(eventType)) {
            System.out.println("Evento messages.update detectado.");
            Map<String, Object> updateData = payload.getDataAsMap(); // Use getDataAsMap()

            if (updateData != null && updateData.containsKey("keyId")) {
                String messageIdWebhook = (String) updateData.get("keyId"); // Use keyId do payload
                String remoteJid = (String) updateData.get("remoteJid");
                String statusWebhook = (String) updateData.get("status");

                System.out.println("Atualização para a mensagem ID (keyId): " + messageIdWebhook + ", remoteJid: " + remoteJid + ", status: " + statusWebhook);

                if ("DELIVERY_ACK".equals(statusWebhook)) {
                    System.out.println("Mensagem ENTREGUE: " + messageIdWebhook);
                    if (remoteJid != null && remoteJid.endsWith("@s.whatsapp.net")) {
                        String telefone = remoteJid.replace("@s.whatsapp.net", "");
                        MensagemNaFila mensagemNaFila = mensagemNaFilaRepository.findTopByTelefoneDestinoAndStatusEnvioOrderByDataEnvioRealizadoDesc(telefone, "ENVIADO");

                        if (mensagemNaFila != null) {
                            mensagemNaFila.setStatusEnvio("ENTREGUE");
                            mensagemNaFilaRepository.save(mensagemNaFila);
                            System.out.println("Status da MensagemNaFila atualizado para ENTREGUE: " + mensagemNaFila.getId());

                            scheduling agendamento = schedulingRepository.findByTelefone(telefone);
                            if (agendamento != null) {
                                agendamento.setLembreteEnviado(true);
                                agendamento.setDataUltimoLembreteEnviado(LocalDateTime.now());
                                schedulingRepository.save(agendamento);
                                System.out.println("Status do lembrete atualizado para enviado (via entrega) para o telefone: " + telefone);
                            }
                        }
                    }
                } else if ("DELIVERY_ERROR".equals(statusWebhook)) {
                    System.out.println("Falha na entrega da mensagem: " + messageIdWebhook);
                    if (remoteJid != null && remoteJid.endsWith("@s.whatsapp.net")) {
                        String telefone = remoteJid.replace("@s.whatsapp.net", "");
                        MensagemNaFila mensagemNaFila = mensagemNaFilaRepository.findTopByTelefoneDestinoAndStatusEnvioOrderByDataEnvioRealizadoDesc(telefone, "ENVIADO");
                        if (mensagemNaFila != null) {
                            mensagemNaFila.setStatusEnvio("FALHA");
                            mensagemNaFilaRepository.save(mensagemNaFila);
                            System.out.println("Status da MensagemNaFila atualizado para FALHA: " + mensagemNaFila.getId());
                        }
                    }
                }
            } else {
                System.out.println("Dados incompletos para messages.update.");
            }
        } else {
            System.out.println("Evento não tratado: " + eventType);
        }
    }
}