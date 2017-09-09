package de.snuk.wsbroker;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguraiton extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
	registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
	registry.setApplicationDestinationPrefixes("/app");
	registry.enableStompBrokerRelay("/topic", "/queue").setRelayHost("192.168.56.101").setRelayPort(61613)
		.setClientLogin("guest").setClientPasscode("guest")
		.setUserDestinationBroadcast("/topic/unresolved-user-dest");
    }

}
