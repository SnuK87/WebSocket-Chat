package de.snuk.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class ChatConfig {

    @Bean
    public WebSocketSessionEventListener myEventListener(SimpMessagingTemplate messagingTemplate) {
	WebSocketSessionEventListener listener = new WebSocketSessionEventListener(messagingTemplate);

	return listener;
    }

}
