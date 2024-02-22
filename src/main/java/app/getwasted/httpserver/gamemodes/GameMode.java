package app.getwasted.httpserver.gamemodes;

import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.requests.WebSocketRequest;
import app.getwasted.httpserver.responses.CurrentGameState;
import app.getwasted.httpserver.responses.gamemodes.GameData;
import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;

@Getter
public abstract class GameMode {
    protected Lobby lobby;
    private AbstractObject settings;
    protected String state;
    protected int stateId;

    public GameMode(Lobby lobby, AbstractObject settings) {
        this.lobby = lobby;
        this.settings = settings;
    }


    public void start() {}
    public void stop() {}

    public void setState(String state) {
        this.stateId++;
        this.state = state;
        lobby.broadcastCurrentState();
    }

    public void broadcast(Object obj) {
        for (UserSession user : lobby.getUsers()) {
            user.send(obj);
        }
    }

    public abstract void handleRequest(UserSession user, Class<? extends WebSocketRequest> type, WebSocketRequest webSocketRequest);

    public CurrentGameState getCurrentState(UserSession user) {
        return null;
    }
}
