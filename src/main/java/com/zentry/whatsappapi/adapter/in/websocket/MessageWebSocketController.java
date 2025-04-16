package com.zentry.whatsappapi.adapter.in.websocket;

import com.zentry.whatsappapi.domain.model.MessageEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Esse método pode ser chamado para enviar uma mensagem a todos conectados
    public void broadcastMessage(MessageEvent messageEvent) {
        messagingTemplate.convertAndSend("/topic/messages", messageEvent);
    }

    // Opcional: se quiser permitir que o front envie mensagens via websocket também
    @MessageMapping("/send") // front envia pra /app/send
    public void receiveFromFront(MessageEvent messageEvent) {
        System.out.println("Recebido do front via WebSocket: " + messageEvent);
        // Aqui você pode tratar a mensagem, salvar no banco, etc
        broadcastMessage(messageEvent);
    }
}
