package app.getwasted.httpserver.gamemodes.hundredquestions;

import app.getwasted.httpserver.gamemodes.GameMode;
import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.requests.WebSocketRequest;
import app.getwasted.httpserver.responses.gamemodes.GameData;
import org.javawebstack.abstractdata.AbstractObject;

public class HundredQuestionsGameMode extends GameMode {
    public HundredQuestionsGameMode(Lobby lobby, AbstractObject settings) {
        super(lobby, settings);
    }

    public GameData getGameData(UserSession user) {
        return null;
    }

    public void handleRequest(UserSession user, Class<? extends WebSocketRequest> type, WebSocketRequest webSocketRequest) {

    }
}
