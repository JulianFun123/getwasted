package app.getwasted.httpserver.responses;

import lombok.Data;

@Data
public class CreateLobbyResponse {
    private String lobbyCode;
    private String userKey;

    public CreateLobbyResponse(String lobbyCode, String userKey) {
        this.lobbyCode = lobbyCode;
        this.userKey = userKey;
    }

}
