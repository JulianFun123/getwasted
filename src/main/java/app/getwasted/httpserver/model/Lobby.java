package app.getwasted.httpserver.model;

import app.getwasted.httpserver.GetWastedServer;
import app.getwasted.httpserver.gamemodes.GameMode;
import app.getwasted.httpserver.gamemodes.GameModes;
import app.getwasted.httpserver.requests.WebSocketRequest;
import app.getwasted.httpserver.requests.gamemodes.StartGameRequest;
import app.getwasted.httpserver.requests.lobbies.StartSelectionRequest;
import app.getwasted.httpserver.responses.CurrentLobbyState;
import app.getwasted.httpserver.responses.CurrentUserResponse;
import app.getwasted.httpserver.responses.UserResponse;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.javawebstack.abstractdata.AbstractObject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Lobby {
    private final String id;
    private final String code;

    private GetWastedServer getWastedServer;

    private State state = State.AWAITING;
    private int stateId = 0;

    private GameMode gameMode = null;


    public Lobby(GetWastedServer getWastedServer) {
        this.getWastedServer = getWastedServer;
        id = RandomStringUtils.random(6, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890");
        code = RandomStringUtils.random(6, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
    }

    public List<UserSession> getUsers() {
        return getWastedServer.getUsers().stream().filter(u -> u.getLobby() == this).collect(Collectors.toList());
    }

    public void userRequest(UserSession user, Class<? extends WebSocketRequest> type, WebSocketRequest webSocketRequest) {
        if (user.getType() == UserSession.Type.ADMIN) {
            if (type == StartSelectionRequest.class) {
                this.setState(State.SELECTING);
                this.broadcastCurrentState();
            } else if (type == StartGameRequest.class) {
                StartGameRequest request = (StartGameRequest) webSocketRequest;

                GameModes gameModeType = GameModes.valueOf(request.game);
                try {
                    gameMode = gameModeType.getClazz().getDeclaredConstructor(Lobby.class, AbstractObject.class).newInstance(this, request.settings);
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                this.setState(State.INGAME);
                gameMode.start();
                this.broadcastCurrentState();
            }
        }

        if (gameMode != null) {
            gameMode.handleRequest(user, type, webSocketRequest);
        }
    }

    public void sendCurrentState(UserSession user) {
        user.send(new CurrentLobbyState(
                code,
                state,
                stateId,
                getGameType(),
                gameMode == null ? null : gameMode.getCurrentState(user),
                getUsers().stream().map(u -> new UserResponse(u)).collect(Collectors.toList()),
                new CurrentUserResponse(user)
        ));
    }

    public void broadcastCurrentState() {
        getUsers().forEach(this::sendCurrentState);
    }

    public GameModes getGameType() {
        if (gameMode == null)
            return null;

        for (GameModes value : GameModes.values()) {
            if (gameMode.getClass() == value.getClazz()) {
                return value;
            }
        }
        return null;
    }

    public void setState(State state) {
        stateId++;
        this.state = state;
    }

    public enum State {
        AWAITING,
        SELECTING,
        INGAME
    }
}
