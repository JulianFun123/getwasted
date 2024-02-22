package app.getwasted.httpserver;

import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.requests.CreateLobbyRequest;
import app.getwasted.httpserver.responses.CreateLobbyResponse;
import app.getwasted.httpserver.websocket.MainWebsocket;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.javawebstack.httpserver.HTTPServer;
import org.javawebstack.webutils.config.Config;
import org.javawebstack.webutils.config.EnvFile;
import org.javawebstack.webutils.middleware.CORSPolicy;
import org.javawebstack.webutils.middleware.MultipartPolicy;
import org.javawebstack.webutils.middleware.SerializedResponseTransformer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetWastedServer {
    private static GetWastedServer instance;

    @Getter
    private final Gson gson = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static GetWastedServer getInstance() {
        return instance;
    }

    private Config config;
    private HTTPServer httpServer;

    private final List<Lobby> lobbies = new ArrayList<>();
    private final List<UserSession> users = new ArrayList<>();

    public GetWastedServer() {
        config = new Config();
        httpServer = new HTTPServer();

        setupConfig();
        setupServer();
    }

    protected void setupConfig() {
        File file = new File(".env");
        if (file.exists()) {
            config.add(new EnvFile(file).withVariables());
        } else {
            config.add(new EnvFile().withVariables());
        }
    }

    protected void setupServer() {
        httpServer.port(config.getInt("http.server.port", 80));

        if (config.has("http.server.cors")) {
            httpServer.beforeInterceptor(new CORSPolicy(config.get("http.server.cors")));
        }

        httpServer.beforeInterceptor(new MultipartPolicy(config.get("http.server.tmp", null)));
        httpServer.responseTransformer(new SerializedResponseTransformer().ignoreStrings());

        /* httpServer.exceptionHandler((exchange, throwable) -> {
            if (throwable instanceof RuntimeException) {
                if (throwable.getCause() != null)
                    throwable = throwable.getCause();
            }

            exchange.status(500);

            return "{}";
        }); */


        httpServer.beforeInterceptor(exchange -> {
            exchange.header("Server", "GetWasted :)");

            return false;
        });

        httpServer.post("/lobby", e -> {
            CreateLobbyRequest req = e.body(CreateLobbyRequest.class);
            Lobby lobby = this.addLobby();
            UserSession user = addUser(req.username, lobby);
            user.setType(UserSession.Type.ADMIN);

            return new CreateLobbyResponse(lobby.getCode(), user.getKey());
        });

        httpServer.webSocket("/lobby/{code}", new MainWebsocket(this));

        httpServer.get("/", e -> {
            try {
                e.write(GetWastedServer.class.getClassLoader().getResourceAsStream("static/index.html"));
                return "";
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        httpServer.staticResourceDirectory("/", "static");
    }

    public void start() {
        httpServer.start();
    }

    public static void main(String[] args) {
        instance = new GetWastedServer();
        instance.start();
    }

    public HTTPServer getHttpServer() {
        return httpServer;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    public List<UserSession> getUsers() {
        return users;
    }

    public UserSession getUser(String code) {
        UserSession userSession = null;

        for (UserSession user : users) {
            if (user.getId().equals(code)) {
                userSession = user;
            }
        }

        return userSession;
    }

    public UserSession getUserBySession(String code) {
        UserSession userSession = null;

        for (UserSession user : users) {
            System.out.println(user.getKey());
            if (user.getKey().equals(code)) {
                userSession = user;
            }
        }

        return userSession;
    }

    public Lobby getLobby(String code) {
        Lobby lobby = null;

        for (Lobby listLobby : lobbies) {
            if (listLobby.getId().equals(code)) {
                lobby = listLobby;
            }
        }

        return lobby;
    }

    public Lobby getLobbyByCode(String code) {
        Lobby lobby = null;

        for (Lobby listLobby : lobbies) {
            if (listLobby.getCode().equals(code)) {
                lobby = listLobby;
            }
        }

        return lobby;
    }

    public UserSession addUser(String user, Lobby lobby) {
        UserSession userSession = new UserSession(this, user, lobby);
        users.add(userSession);
        return userSession;
    }

    public Lobby addLobby() {
        Lobby lobby = new Lobby(this);
        lobbies.add(lobby);
        return lobby;
    }

    public Config getConfig() {
        return config;
    }
}