package app.getwasted.httpserver.gamemodes;

import app.getwasted.httpserver.gamemodes.cardsagainsthumanity.CardsAgainstHumanityGameMode;
import app.getwasted.httpserver.gamemodes.citycountryriver.CityCountryRiverGameMode;
import app.getwasted.httpserver.gamemodes.hundredquestions.HundredQuestionsGameMode;
import lombok.Getter;

public enum GameModes {
    CARDS_AGAINST_HUMANITY(CardsAgainstHumanityGameMode.class),
    HUNDRED_QUESTIONS(HundredQuestionsGameMode.class),
    WOULD_YOU_RATHER(HundredQuestionsGameMode.class),
    CITY_COUNTRY_RIVER(CityCountryRiverGameMode.class);

    @Getter
    private Class<? extends GameMode> clazz;

    GameModes(Class<? extends GameMode> clazz) {
        this.clazz = clazz;
    }


}
