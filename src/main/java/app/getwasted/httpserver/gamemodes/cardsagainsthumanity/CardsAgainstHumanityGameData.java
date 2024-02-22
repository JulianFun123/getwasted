package app.getwasted.httpserver.gamemodes.cardsagainsthumanity;

import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.responses.gamemodes.GameData;

import java.util.ArrayList;
import java.util.List;

public class CardsAgainstHumanityGameData extends GameData {
    public String blackCard;
    public int round;
    public int roundMax;

    public List<String> availableCards;

    public CardsAgainstHumanityGameData(UserSession user, CardsAgainstHumanityGameMode mode) {
        this.blackCard = mode.getCurrentBlackCard();
        this.round = mode.getRound();
        this.roundMax = mode.getRoundMax();

        availableCards = mode.getCardAssignments().get(user);
    }
}
