package de.snuk.websocket;

public class Greeting {

    private String time;
    private String user;
    private String content;

    public Greeting() {
    }

    public Greeting(String time, String user, String content) {
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