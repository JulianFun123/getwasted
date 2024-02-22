package app.getwasted.httpserver.websocket;

import app.getwasted.httpserver.GetWastedServer;
import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.requests.WebSocketRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.javawebstack.httpserver.Exchange;
import org.javawebstack.httpserver.handler.WebSocketHandler;
import org.javawebstack.httpserver.websocket.WebSocket;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainWebsocket implements WebSocketHandler {

    public GetWastedServer getWastedServer;

    private Reflections reflections;
    private String reflectionName;
    private Map<String, Class<? extends WebSocketRequest>> webSocketRequestClasses = new HashMap<>();



    public MainWebsocket(GetWastedServer getWastedServer) {
        this.getWastedServer = getWastedServer;

        reflectionName = WebSocketRequest.class.getPackage().getName();

        reflections = new Reflections(reflectionName);

        reflections.getSubTypesOf(WebSocketRequest.class).forEach(clazz -> {
            webSocketRequestClasses.put(clazz.getName().replace(reflectionName + ".", ""), clazz);
        });
    }


    public void onConnect(WebSocket webSocket) {
        Exchange exchange = webSocket.getExchange();

        Lobby lobby = getWastedServer.getLobbyByCode(exchange.path("code"));

        System.out.println(lobby);
        if (lobby == null) {
            webSocket.close();
            return;
        }

        UserSession user = getWastedServer.getUserBySession(exchange.query("user_key"));

        if (user == null) {
            System.out.println(exchange.query("name"));
            System.out.println(lobby);
            user = getWastedServer.addUser(exchange.query("name"), lobby);
        }

        user.setWebSocket(webSocket);

        exchange.attrib("user", user);
        user.setOnline(false);

        System.out.println("Connected " + user.getName() + " on " + lobby.getCode());

        lobby.broadcastCurrentState();
    }



    public void onMessage(WebSocket webSocket, String s) {
        UserSession user = webSocket.getExchange().attrib("user");

        WebSocketRequest webSocketRequest = this.getWastedServer.getGson().fromJson(s, WebSocketRequest.class);

        Class<? extends WebSocketRequest> request = webSocketRequestClasses.get(webSocketRequest.type);

        user.handleRequest(request, this.getWastedServer.getGson().fromJson(s, request));
    }

    public void onMessage(WebSocket webSocket, byte[] bytes) {
        System.out.println("???");
    }


    @Override
    public void onClose(WebSocket webSocket, Integer integer, String s) {
        UserSession user = webSocket.getExchange().attrib("user");
        if (user != null) {
            user.setOnline(false);
        }
    }
}
