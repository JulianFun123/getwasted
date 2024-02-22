import Page from "../Page.js";
import {$n} from "../helper.js";
import GameSelection from "../pages/GameSelection.js";

export default class SelectionPage extends Page {
    render() {
        this.getWasted.$screen
            .html('')
            .append(
                $n('a').text('Zurück zur Spielauswahl')
                    .click(() => {
                        this.getWasted.setScreen(new GameSelection(this.getWasted))
                    })
            )
    }
}