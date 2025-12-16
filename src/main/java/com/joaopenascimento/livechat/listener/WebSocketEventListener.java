package com.joaopenascimento.livechat.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        Principal user = event.getUser();

        if (user != null) {
            String username = user.getName();
            logger.info("Novo usuário conectado: " + username);

            messagingTemplate.convertAndSend("/topics/status", username + " entrou no chat.");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal user = event.getUser();

        if (user != null) {
            String username = user.getName();
            logger.info("Usuário desconectado: " + username);

            messagingTemplate.convertAndSend("/topics/status", username + " saiu do chat.");
        }
    }
}