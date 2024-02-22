package app.getwasted.httpserver.requests;

public class WebSocketRequest {
    public String type;

    public WebSocketRequest() {
        this.type = getClass().getName();
    }
}
