package app.getwasted.httpserver.requests.gamemodes;

import app.getwasted.httpserver.requests.WebSocketRequest;
import org.javawebstack.abstractdata.AbstractObject;

public class StartGameRequest extends WebSocketRequest {
    public String game = "";

    public AbstractObject settings = new AbstractObject();
}