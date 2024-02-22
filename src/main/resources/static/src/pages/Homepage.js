import Page from "../Page.js";
import {$n} from "../helper.js";
import CreateLobby from "./CreateLobby.js";
import JoinLobby from "./JoinLobby.js";

export default class Homepage extends Page {

    render() {
        this.getWasted.$screen
            .html('')
            .append(
                $n('h1')
                    .addClass('page-title')
                    .addClass('mt-5')
                    .addClass('mb-5')
                    .text('Wilkommen!')
            )
            .append(
                $n('h2')
                    .addClass('page-sub-title')
                    .addClass('mb-8')
                    .text('Merspieler Spiele für Saufabende!')
            )
            .append(
                $n('button')
                    .addClass('btn')
                    .addClass('full-width')
                    .text('Gruppe erstellen')
                    .addClass('mb-4')
                    .click(() => {
                        this.getWasted.setScreen(new CreateLobby(this.getWasted))
                    })
            )
            .append(
                $n('button')
                    .addClass('btn')
                    .addClass('full-width')
                    .addClass('secondary')
                    .addClass('mb-4')
                    .text('Gruppe beitreten')
                    .click(() => {
                        this.getWasted.setScreen(new JoinLobby(this.getWasted))
                    })
            )

        if (localStorage['last_session']) {
            this.getWasted.$screen
                .append(
                    $n('label')
                        .text('Du bist letztens aus einer Runde gegangen, bevor sie zuende war. Möchtest du wieder beitreten?')
                )
                .append(
                    $n('button')
                        .addClass('btn')
                        .addClass('full-width')
                        .addClass('secondary')
                        .addClass('mb-4')
                        .text('Letzte Gruppe wieder beitreten')
                        .click(() => {
                            this.getWasted.joinLastGame()
                        })
                )
        }
    }
}