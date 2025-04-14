package com.zentry.whatsappapi.domain.model;

import org.springframework.data.annotation.Id;
import  org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "contact_updates")

public class ContactUpdate {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactUpdate that = (ContactUpdate) o;
        return Objects.equals(profilePicUrl, that.profilePicUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(profilePicUrl);
    }

    @Id
    private String id; // O ID do documento no MongoDB

    private String remoteJid;
    private String profilePicUrl;
    private String instanceId; // Podemos incluir o instanceId também, se útil

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemoteJid() {
        return remoteJid;
    }

    public void setRemoteJid(String remoteJid) {
        this.remoteJid = remoteJid;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String toString() {
        return "ContactUpdate{" +
                "id='" + id + '\'' +
                ", remoteJid='" + remoteJid + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }

}
