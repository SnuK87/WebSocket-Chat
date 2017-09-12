package de.snuk.websocket.config;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    // @Autowired
    // private WebSocketMessageBrokerStats webSocketMessageBrokerStats;
    //
    // @PostConstruct
    // public void init() {
    // webSocketMessageBrokerStats.setLoggingPeriod(10 * 1000);
    // }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
	config.setApplicationDestinationPrefixes("/app");
	config.enableSimpleBroker("/topic", "/queue");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
	registry.addEndpoint("/ws").setHandshakeHandler(new ChatHandshakeHandler()).withSockJS();

    }

    private class ChatHandshakeHandler extends DefaultHandshakeHandler {

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
		Map<String, Object> attributes) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),
		    authentication.getAuthorities());
	}
    }

}
