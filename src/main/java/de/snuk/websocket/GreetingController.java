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
public class GreetingController {

    private static final int CAPACITIY = 5;

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Queue<Greeting> lastMessages = new LinkedBlockingQueue<>(CAPACITIY);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
	Thread.sleep(500); // simulated delay

	Greeting greeting = new Greeting(FORMATTER.format(LocalTime.now()), message.getName(), message.getMessage());
	addMessage(greeting);

	return greeting;
    }

    @GetMapping("/latest")
    public List<Greeting> latest() {
	return new ArrayList<>(lastMessages);
    }

    synchronized void addMessage(Greeting message) {
	if (lastMessages.size() == CAPACITIY) {
	    lastMessages.poll();
	}

	lastMessages.add(message);
    }
}