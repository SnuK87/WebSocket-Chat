package de.snuk.websocket;

import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import de.snuk.websocket.model.ChatMessage;
import de.snuk.websocket.model.UserMessage;

@RestController
public class ChatController {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage message(UserMessage message, Principal user) throws Exception {
	ChatMessage chatmessage = new ChatMessage(FORMATTER.format(LocalTime.now()), user.getName(),
		message.getMessage());

	return chatmessage;
    }
}