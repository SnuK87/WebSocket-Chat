# Spring-Websocket-Playground
Demo projects to learn about spring-websockets.

Docs:
https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html
https://docs.spring.io/spring-framework/docs/4.1.0.RC1/spring-framework-reference/html/websocket.html#websocket-stomp-appplication-context-events

Guides / Examples:
https://spring.io/guides/gs/messaging-stomp-websocket/
https://github.com/salmar/spring-websocket-chat

Talks:
https://www.youtube.com/watch?v=nxakp15CACY

## chat-app
Simple chat application that puts everything together.

## without-stomp
Using custom text and binary handler without SockJS and STOMP

## secure-websocket
Securing websocket messages and subscription endpoints. Usign custom handshake handler to identify users and enable direct messages.

## websocket-custom-broker
Using rabbitmq as stomp broker relay for scaling. (Rabbitmq server with stomp plugin in docker-compose file) 
