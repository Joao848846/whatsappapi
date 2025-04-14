package com.zentry.whatsappapi.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Document(collection = "send_messages") // nova collection no banco
public class SendMessages {

    @Id
    private String id;


    private String event;
    private String instance;
    private String remoteJid;
    private String messageId;
    private String conversation;
    private String messageType;
    private Long messageTimestamp; // <- Alterado para Long (melhor p/ trabalhar com epoch time)
    private LocalDateTime dateTime;
    private String sender;


    public SendMessages() {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SendMessages{" +
                "id='" + id + '\'' +
                ", event='" + event + '\'' +
                ", instance='" + instance + '\'' +
                ", remoteJid='" + remoteJid + '\'' +
                ", messageId='" + messageId + '\'' +
                ", conversation='" + conversation + '\'' +
                ", messageType='" + messageType + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                ", dateTime=" + dateTime +
                ", sender='" + sender + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getEvent() {
        return event;
    }

    public String getInstance() {
        return instance;
    }

    public String getRemoteJid() {
        return remoteJid;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getConversation() {
        return conversation;
    }

    public String getMessageType() {
        return messageType;
    }

    public Long getMessageTimestamp() {
        return messageTimestamp;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getSender() {
        return sender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void setRemoteJid(String remoteJid) {
        this.remoteJid = remoteJid;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setMessageTimestamp(Long messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void save(SendMessages sendMessages) {

    }
}
