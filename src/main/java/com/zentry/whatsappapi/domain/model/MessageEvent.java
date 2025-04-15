package com.zentry.whatsappapi.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Document(collection = "message_events") // Nova collection unificada
public class MessageEvent {

    @Id
    private String id;

    private String event;
    private String instance;
    private String remoteJid;
    private String messageId;
    private String conversation;
    private String messageType;
    private Long messageTimestamp; // Para epoch time
    private LocalDateTime dateTime;
    private String sender;

    // Atributo para diferenciar se é enviada ou recebida
    private boolean fromMe; // true: enviada, false: recebida

    // Adicionando o campo 'pushName' que existe em Messages
    private String pushName;

    // Construtores, getters e setters...

    public MessageEvent() {
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
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
                ", fromMe=" + fromMe +
                ", pushName='" + pushName + '\'' +
                '}';
    }

    // Getters e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getRemoteJid() {
        return remoteJid;
    }

    public void setRemoteJid(String remoteJid) {
        this.remoteJid = remoteJid;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Long getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(Long messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public String getPushName() {
        return pushName;
    }

    public void setPushName(String pushName) {
        this.pushName = pushName;
    }
}
