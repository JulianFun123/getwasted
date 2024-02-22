package app.getwasted.httpserver.gamemodes.cardsagainsthumanity.data;

import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.CardsAgainstHumanityGameData;
import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.CardsAgainstHumanityGameMode;
import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.CardsAgainstHumanityGameState;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.responses.gamemodes.GameData;

public class PickCardsData extends CardsAgainstHumanityGameData {
    public String picked;

    public PickCardsData(UserSession user, CardsAgainstHumanityGameMode mode) {
        super(user, mode);
        this.picked = mode.getCurrentPickedCards().get(user.getId());
    }
}
