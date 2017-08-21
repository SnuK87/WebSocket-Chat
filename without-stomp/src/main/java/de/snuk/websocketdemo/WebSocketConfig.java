package de.snuk.websocketdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Bean
	public WebSocketHandler binHandler() {
		return new MyBinaryHandler();
	}

	@Bean
	public WebSocketHandler textHandler() {
		return new MyTextHandler();
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(textHandler(), "/ws/text").setAllowedOrigins("*");
		registry.addHandler(binHandler(), "/ws/bin").setAllowedOrigins("*");
	}
}
