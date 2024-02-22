package app.getwasted.httpserver.gamemodes.cardsagainsthumanity;

import app.getwasted.httpserver.gamemodes.GameMode;
import app.getwasted.httpserver.gamemodes.GameModes;
import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.data.PickCardsData;
import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.data.ShowCardsData;
import app.getwasted.httpserver.model.Lobby;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.requests.WebSocketRequest;
import app.getwasted.httpserver.requests.gamemodes.cardsagainsthumanity.CardPickedRequest;
import app.getwasted.httpserver.responses.CurrentGameState;
import app.getwasted.httpserver.responses.CurrentLobbyState;
import app.getwasted.httpserver.responses.UserResponse;
import app.getwasted.httpserver.responses.gamemodes.GameData;
import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.abstractdata.AbstractPrimitive;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class CardsAgainstHumanityGameMode extends GameMode {

    private final List<String> whiteCards = new ArrayList<>();
    private final List<String> blackCards = new ArrayList<>();
    private final Random random = new Random();

    private String currentBlackCard = null;
    private Map<String, String> currentPickedCards = new HashMap<>();
    private Map<String, List<UserSession>> currentPickedCardVotes = new HashMap<>();

    private int timeLeft;

    private int round;
    private int roundMax;

    private final Map<UserSession, List<String>> cardAssignments = new HashMap<>();

    public CardsAgainstHumanityGameMode(Lobby lobby, AbstractObject settings) {
        super(lobby, settings);

        setState(CardsAgainstHumanityGameState.WAITING);

        roundMax = settings.get("round_max", AbstractPrimitive.from(5)).number().intValue();
    }

    public void startPickCardState() {
        setState(CardsAgainstHumanityGameState.WAITING);

        System.out.println("___----: " + (blackCards.size() - 1));
        currentBlackCard = blackCards.get(random.nextInt(blackCards.size() - 1));
        currentPickedCards = new HashMap<>();

        setState(CardsAgainstHumanityGameState.PICK_CARDS);
    }

    public void startShowCardsState() {
        setState(CardsAgainstHumanityGameState.SHOW_CARDS);
    }


    public void start() {
        whiteCards.add("Getting swole");
        whiteCards.add("Hitler");
        whiteCards.add("Kock");
        whiteCards.add("Schwänz");
        whiteCards.add("Worttest");
        whiteCards.add("OHa");
        whiteCards.add("lolololololo");
        whiteCards.add("lolololofd");
        whiteCards.add("lolollo");
        whiteCards.add("lolololo");

        blackCards.add("Wenn ich was essen könnte, wäre es _.");
        blackCards.add("Ich werde mit _ koksen.");

        for (UserSession user : lobby.getUsers()) {
            this.assignCards(5, user);
        }

        this.startPickCardState();
    }

    public void handleRequest(UserSession user, Class<? extends WebSocketRequest> type, WebSocketRequest webSocketRequest) {
        if (type == CardPickedRequest.class) {
            CardPickedRequest cardPickedRequest = (CardPickedRequest) webSocketRequest;

            currentPickedCards.put(user.getId(), cardPickedRequest.card);
            cardAssignments.get(user).remove(cardPickedRequest.card);

            if (lobby.getUsers().stream().filter(UserSession::isOnline).count() <= currentPickedCards.size()) {
                startShowCardsState();
            }
        }

        if (round >= roundMax) {
            round = 0;
        }
    }

    public void assignCards(int count, UserSession user) {
        if (!cardAssignments.containsKey(user))
            cardAssignments.put(user, new ArrayList<>());

        for (int i = 0; i < count; i++) {
            cardAssignments.get(user).add(whiteCards.get(random.nextInt(whiteCards.size() - 1)));
        }
    }

    public void setState(CardsAgainstHumanityGameState state) {
        setState(state.name());
    }

    public CurrentGameState getCurrentState(UserSession user) {
        GameData gameData = null;
        switch (CardsAgainstHumanityGameState.valueOf(state)) {
            case PICK_CARDS:
                gameData = new PickCardsData(user, this);
                break;

            case SHOW_CARDS:
                ShowCardsData showCardsData = new ShowCardsData(user, this);
                currentPickedCards.forEach((userId, card) -> {
                    showCardsData.cards.add(new ShowCardsData.ShowCardsSingle(card, new UserResponse(lobby.getGetWastedServer().getUser(userId))));
                });
                gameData = showCardsData;
                break;
        }

        return new CurrentGameState(this, gameData);
    }
}
