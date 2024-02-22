package app.getwasted.httpserver.model;

import app.getwasted.httpserver.GetWastedServer;
import app.getwasted.httpserver.requests.WebSocketRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.javawebstack.httpserver.websocket.WebSocket;

@Data
public class UserSession {
    private final String id;

    private String name;

    private String key;

    private Lobby lobby;

    private boolean online = false;

    private Type type;

    private GetWastedServer getWastedServer;

    private WebSocket webSocket;

    public UserSession(GetWastedServer getWastedServer, String name, Lobby lobby) {
        this.name = name;
        this.lobby = lobby;
        this.getWastedServer = getWastedServer;
        id = RandomStringUtils.random(8, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890");
        key = RandomStringUtils.random(20, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890");
    }


    public void handleRequest(Class<? extends WebSocketRequest> type, WebSocketRequest webSocketRequest) {
        this.lobby.userRequest(this, type, webSocketRequest);
    }

    public void send(Object obj) {
        this.webSocket.send(this.getWastedServer.getGson().toJson(obj));
    }

    public enum Type {
        ADMIN,
        PLAYER,
        VIEWER
    }
}
