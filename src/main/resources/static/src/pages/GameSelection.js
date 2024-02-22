import Page from "../Page.js";
import {$n} from "../helper.js";
import CardsAgainstHumanitySelection from "../gamemodes/cardsagainsthumanity/CardsAgainstHumanitySelection.js";

export default class GameSelection extends Page {
    render() {
        const currentUser = this.getWasted.currentState.current_user
        if (currentUser?.type === 'ADMIN') {
            this.getWasted.$screen
                .html('')
                .append(
                    $n('h1')
                        .addClass('page-title')
                        .addClass('mt-5')
                        .addClass('mb-5')
                        .text('Spiel auswählen')
                )



            for (const categoryName in this.getWasted.gameModes) {
                const category = this.getWasted.gameModes[categoryName]
                this.getWasted.$screen
                    .append(
                        $n('a')
                            .addClass('gamemode-card')
                            .addClass('mt-4')
                            .append($n('img').attr('src', category.img))
                            .click(category.click)
                    )
            }

        } else {
            this.getWasted.$screen
                .html('')
                .append(
                    $n('h1')
                        .addClass('page-title')
                        .addClass('mt-5')
                        .addClass('mb-5')
                        .text('Warten auf Spieleleiter...')
                )
        }
    }
}