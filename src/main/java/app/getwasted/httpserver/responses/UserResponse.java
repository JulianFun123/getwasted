package app.getwasted.httpserver.responses;

import app.getwasted.httpserver.model.UserSession;

public class UserResponse {
    public String id;
    public String name;
    public UserResponse(UserSession user) {
        this.id = user.getId();
        this.name = user.getName();
    }

}
