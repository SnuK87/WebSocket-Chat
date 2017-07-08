package de.snuk.websocket;

public class ChatMessage {

    private String time;
    private String user;
    private String content;

    public ChatMessage() {
    }

    public ChatMessage(String time, String user, String content) {
	this.time = time;
	this.user = user;
	this.content = content;
    }

    public String getContent() {
	return content;
    }

    public String getTime() {
	return time;
    }

    public String getUser() {
	return user;
    }
}