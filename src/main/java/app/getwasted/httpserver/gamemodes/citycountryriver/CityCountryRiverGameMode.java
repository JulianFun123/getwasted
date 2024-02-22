package app.getwasted.httpserver.gamemodes.citycountryriver;

import app.getwasted.httpserver.gamemodes.GameMode;
import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.requests.WebSocketRequest;
import app.getwasted.httpserver.responses.gamemodes.GameData;
import org.javawebstack.abstractdata.AbstractObject;

public class CityCountryRiverGameMode extends GameMode {

    public CityCountryRiverGameMode(Lobby lobby, AbstractObject settings) {
        super(lobby, settings);
    }

    @Override
    public void handleRequest(UserSession user, Class<? extends WebSocketRequest> type, WebSocketRequest webSocketRequest) {

    }
}
