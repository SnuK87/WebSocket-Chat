package de.snuk.websocket.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class WebSocketSessionEventListener {

    private SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketSessionEventListener(SimpMessagingTemplate simpMessagingTemplate) {
	this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
	simpMessagingTemplate.convertAndSend("/topic/chat/login", event.getUser().getName());
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
	simpMessagingTemplate.convertAndSend("/topic/chat/logout", event.getUser().getName());
    }

}
