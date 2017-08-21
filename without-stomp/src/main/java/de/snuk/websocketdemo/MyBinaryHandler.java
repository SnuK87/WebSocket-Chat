package de.snuk.websocketdemo;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class MyBinaryHandler extends BinaryWebSocketHandler {

	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
		System.out.println("incoming message: " + message.getPayload());
		ByteBuffer payload = message.getPayload();
		byte[] array = payload.array();
		System.out.println(new String(array));
		session.sendMessage(new BinaryMessage("hello wordl".getBytes()));
	}
}
