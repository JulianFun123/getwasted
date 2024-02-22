import SelectionPage from "../SelectionPage.js";
import {$n} from "../../helper.js";

export default class CardsAgainstHumanitySelection extends SelectionPage {
    render() {
        super.render()

        this.getWasted.$screen
            .append(
                $n('button')
                    .addClass('btn')
                    .addClass('full-width')
                    .addClass('mb-4')
                    .text('Spiel starten')
                    .click(async () => {
                        await this.getWasted.socket
                            .startGame('CARDS_AGAINST_HUMANITY', {})
                    })
            )
    }
}