package de.snuk.securesocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
	messages.simpSubscribeDestMatchers("/topic/greetings").hasRole("USER").anyMessage().authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
	return true;
    }
}
