package app.getwasted.httpserver.responses;

import app.getwasted.httpserver.model.UserSession;

public class CurrentUserResponse extends UserResponse {
    public UserSession.Type type;
    private String userKey;

    public CurrentUserResponse(UserSession user) {
        super(user);

        this.type = user.getType();
        this.userKey = user.getKey();
    }

}
