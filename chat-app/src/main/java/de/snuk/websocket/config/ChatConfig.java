package de.snuk.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import de.snuk.websocket.ChatUserRepository;

@Configuration
public class ChatConfig {

    @Bean
    public WebSocketSessionEventListener myEventListener(SimpMessagingTemplate messagingTemplate) {
	WebSocketSessionEventListener listener = new WebSocketSessionEventListener(messagingTemplate, userRepository());

	return listener;
    }

    @Bean
    public ChatUserRepository userRepository() {
	return new ChatUserRepository();
    }

}
