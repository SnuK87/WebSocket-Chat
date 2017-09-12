package de.snuk.websocket;

import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import de.snuk.websocket.model.ChatMessage;
import de.snuk.websocket.model.UserMessage;

@RestController
public class ChatController {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @SubscribeMapping("/chat/users")
    public String retrieveUsers() {
	return "This should be a list containing all users";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage message(@Payload UserMessage message, Principal user) throws Exception {
	ChatMessage chatmessage = new ChatMessage(FORMATTER.format(LocalTime.now()), user.getName(),
		message.getMessage());

	return chatmessage;
    }

    @MessageMapping("/chat/private/{username}")
    public void privateMessage(@Payload UserMessage message, @DestinationVariable("username") String username,
	    Principal user) {

	ChatMessage chatMessage = new ChatMessage(FORMATTER.format(LocalTime.now()), user.getName(),
		message.getMessage());

	// simpMessagingTemplate.convertAndSendToUser(user.getName(), destination,
	// payload);

    }
}