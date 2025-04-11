package com.zentry.whatsappapi.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "messages") // <- Corrigido aqui!
public class Messages {

    @Id
    private String id;

    private String event;
    private String instance;
    private String remoteJid;
    private boolean fromMe;
    private String messageId;
    private String pushName;
    private String conversation;
    private String messageType;
    private Long messageTimestamp; // <- Alterado para Long (melhor p/ trabalhar com epoch time)
    private String instanceId;
    private LocalDateTime dateTime;
    private String sender;

    // Getters e Setters


    @Override
    public String toString() {
        return "Messages{" +
                "id='" + id + '\'' +
                ", event='" + event + '\'' +
                ", instance='" + instance + '\'' +
                ", remoteJid='" + remoteJid + '\'' +
                ", fromMe=" + fromMe +
                ", messageId='" + messageId + '\'' +
                ", pushName='" + pushName + '\'' +
                ", conversation='" + conversation + '\'' +
                ", messageType='" + messageType + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                ", instanceId='" + instanceId + '\'' +
                ", dateTime=" + dateTime +
                ", sender='" + sender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Messages messages = (Messages) o;
        return fromMe == messages.fromMe && Objects.equals(id, messages.id) && Objects.equals(event, messages.event) && Objects.equals(instance, messages.instance) && Objects.equals(remoteJid, messages.remoteJid) && Objects.equals(messageId, messages.messageId) && Objects.equals(pushName, messages.pushName) && Objects.equals(conversation, messages.conversation) && Objects.equals(messageType, messages.messageType) && Objects.equals(messageTimestamp, messages.messageTimestamp) && Objects.equals(instanceId, messages.instanceId) && Objects.equals(dateTime, messages.dateTime) && Objects.equals(sender, messages.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, instance, remoteJid, fromMe, messageId, pushName, conversation, messageType, messageTimestamp, instanceId, dateTime, sender);
    }

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

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPushName() {
        return pushName;
    }

    public void setPushName(String pushName) {
        this.pushName = pushName;
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

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
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

    public String getFromMe() {
        return fromMe ? "true" : "false";
    }
}
