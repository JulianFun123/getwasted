package app.getwasted.httpserver.responses;

import app.getwasted.httpserver.gamemodes.GameMode;
import app.getwasted.httpserver.gamemodes.GameModes;
import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.responses.gamemodes.GameData;

import java.util.List;

public class CurrentLobbyState {
    public String code;
    public Lobby.State state;
    public int stateId;
    public GameModes gameType;
    public CurrentGameState game;
    public List<UserResponse> users;
    public CurrentUserResponse currentUser;

    public CurrentLobbyState(String code, Lobby.State state, int stateId, GameModes gameType, CurrentGameState game, List<UserResponse> users, CurrentUserResponse currentUser) {
        this.code = code;
        this.state = state;
        this.stateId = stateId;
        this.gameType = gameType;
        this.game = game;
        this.users = users;
        this.currentUser = currentUser;
    }
}
