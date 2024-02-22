package app.getwasted.httpserver.gamemodes.cardsagainsthumanity.data;

import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.CardsAgainstHumanityGameData;
import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.CardsAgainstHumanityGameMode;
import app.getwasted.httpserver.model.UserSession;
import app.getwasted.httpserver.responses.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class ShowCardsData extends CardsAgainstHumanityGameData {
    public List<ShowCardsSingle> cards = new ArrayList<>();

    public ShowCardsData(UserSession user, CardsAgainstHumanityGameMode mode) {
        super(user, mode);
    }


    public static class ShowCardsSingle {
        public String card;
        public UserResponse user;

        public ShowCardsSingle(String card, UserResponse user) {
            this.card = card;
            this.user = user;
        }
    }
}
