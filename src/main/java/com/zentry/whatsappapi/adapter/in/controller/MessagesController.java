package com.zentry.whatsappapi.adapter.in.controller;

import com.zentry.whatsappapi.domain.model.Messages;
import com.zentry.whatsappapi.application.service.EventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private final EventMessage eventMessage;

    @Autowired
    public MessagesController(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
    }

    @PostMapping
    public Messages saveMessage(@RequestBody Messages message) {
        return eventMessage.saveMessage(message);
    }

}
