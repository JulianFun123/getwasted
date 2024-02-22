package app.getwasted.httpserver.responses;

import app.getwasted.httpserver.gamemodes.GameMode;
import app.getwasted.httpserver.gamemodes.GameModes;
import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.responses.gamemodes.GameData;

import java.util.List;

public class CurrentGameState {
    public String state;
    public int stateId;
    public GameData data;

    public CurrentGameState(GameMode gameMode, GameData data) {
        this.state = gameMode.getState();
        this.stateId = gameMode.getStateId();
        this.data = data;
    }
}
