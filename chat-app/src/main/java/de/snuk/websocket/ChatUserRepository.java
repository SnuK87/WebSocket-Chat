package de.snuk.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatUserRepository {

    private Map<String, String> sessions = new ConcurrentHashMap<>();

    public void add(String sessionId, String user) {
	sessions.put(sessionId, user);
    }

    public void remove(String sessionId) {
	sessions.remove(sessionId);
    }

    public Map<String, String> getSessions() {
	return sessions;
    }
}
