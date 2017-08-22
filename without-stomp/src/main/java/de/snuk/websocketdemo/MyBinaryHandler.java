package de.snuk.websocketdemo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class MyBinaryHandler extends BinaryWebSocketHandler {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
	sessions.add(session);
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
	System.out.println("Incoming message: " + new String(message.getPayload().array()));

	for (WebSocketSession s : sessions) {
	    s.sendMessage(new BinaryMessage("just a simple binary response".getBytes()));
	}
    }
}
