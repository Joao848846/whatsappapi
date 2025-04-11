package com.zentry.whatsappapi.application.service;

import com.zentry.whatsappapi.domain.model.Messages;
import com.zentry.whatsappapi.infrastructure.Repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventMessage {

    private final MessagesRepository messagesRepository;

    @Autowired
    public EventMessage(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public Messages saveMessage(Messages message) {
        return messagesRepository.save(message);
    }
}
