package app.getwasted.httpserver.requests.lobbies;

import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.requests.WebSocketRequest;

public class UserLobbyJoinRequest extends WebSocketRequest {
    public UserSession userSession;
}
