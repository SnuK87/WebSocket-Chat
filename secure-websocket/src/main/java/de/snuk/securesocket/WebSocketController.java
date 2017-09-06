package de.snuk.securesocket;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message, Principal principal) throws Exception {

	System.out.println("received: " + message + " by: " + principal.getName());

	return message.toUpperCase();
    }

}
