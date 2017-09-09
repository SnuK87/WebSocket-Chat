# Spring-Websocket-Playground
Demo projects to learn about [spring-websockets](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html)

## chat-app
Simple chat application using spring-websocket with STOMP and SockJS based on https://spring.io/guides/gs/messaging-stomp-websocket/.

## without-stomp
Using custom text and binary handler without SockJS and STOMP

## secure-websocket
Securing websocket messages and subscription endpoints. Usign custom handshake handler to identify users and enable direct messages.

## websocket-custom-broker
Using rabbitmq as stomp broker relay for scaling. (Rabbitmq server with stomp plugin in docker-compose file) 
