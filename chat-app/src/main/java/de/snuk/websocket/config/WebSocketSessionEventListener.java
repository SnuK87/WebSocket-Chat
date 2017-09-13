package de.snuk.websocket.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import de.snuk.websocket.ChatUserRepository;

public class WebSocketSessionEventListener {

    private SimpMessagingTemplate simpMessagingTemplate;
    private ChatUserRepository userRepository;

    public WebSocketSessionEventListener(SimpMessagingTemplate simpMessagingTemplate,
	    ChatUserRepository userRepository) {
	this.simpMessagingTemplate = simpMessagingTemplate;
	this.userRepository = userRepository;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
	System.out.println(event);
	simpMessagingTemplate.convertAndSend("/topic/chat/login", event.getUser().getName());

	SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());

	userRepository.add(headers.getSessionId(), event.getUser().getName());
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
	System.out.println(event);
	simpMessagingTemplate.convertAndSend("/topic/chat/logout", event.getUser().getName());

	SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());

	userRepository.remove(headers.getSessionId());
    }

}
