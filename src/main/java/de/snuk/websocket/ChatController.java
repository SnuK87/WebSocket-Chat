package de.snuk.websocket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private static final int CAPACITIY = 5;

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Queue<ChatMessage> lastMessages = new LinkedBlockingQueue<>(CAPACITIY);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ChatMessage greeting(UserMessage message) throws Exception {
	Thread.sleep(500); // simulated delay

	ChatMessage greeting = new ChatMessage(FORMATTER.format(LocalTime.now()), message.getName(), message.getMessage());
	addMessage(greeting);

	return greeting;
    }

    @GetMapping("/latest")
    public List<ChatMessage> latest() {
	return new ArrayList<>(lastMessages);
    }

    synchronized void addMessage(ChatMessage message) {
	if (lastMessages.size() == CAPACITIY) {
	    lastMessages.poll();
	}

	lastMessages.add(message);
    }
}