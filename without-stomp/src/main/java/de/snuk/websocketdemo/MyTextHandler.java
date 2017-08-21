package de.snuk.websocketdemo;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyTextHandler extends TextWebSocketHandler {

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		System.out.println("incoming message: " + message.getPayload());
		TextMessage msg = new TextMessage("Response: " + message.getPayload());
		session.sendMessage(msg);
	}
}
