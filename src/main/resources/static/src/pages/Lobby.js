import Page from "../Page.js";
import {$n} from "../helper.js";

export default class Lobby extends Page {

    initialized() {
        this.renderUserList()
    }

    onStateUpdate(data) {
        if (data.state === 'AWAITING') {
            this.renderUserList()
        }
    }

    render() {
        this.userList = $n('div')

        this.getWasted.$screen
            .html('')
            .append(
                $n('h1')
                    .addClass('page-title')
                    .addClass('mt-5')
                    .addClass('mb-5')
                    .text('Auf Spieler warten...')
            )
            .append(this.userList)

        const currentUser = this.getWasted.currentState.current_user
        if (currentUser?.type === 'ADMIN') {

            this.getWasted.$screen
                .append(
                    $n('div')
                        .append($n('label').text('CODE'))
                        .append(
                            $n('input')
                                .addClass('input')
                                .setAttr('readonly', 'true')
                                .val(this.getWasted.currentState.code)
                        )
                )
                .append(
                    $n('button')
                        .addClass('btn')
                        .addClass('full-width')
                        .text('Spiel auswählen')
                        .addClass('mt-4')
                        .click(() => {
                            this.getWasted.socket.action('lobbies.StartSelectionRequest')
                        })
                )

        }
    }

    renderUserList() {
        this.userList
            .html('')

        for (const user of this.getWasted.currentState.users) {
            this.userList.append(
                $n('div')
                    .addClass('profile')
                    .append($n('span').addClass('big-letter').text(user.name.substring(0, 1)))
                    .append($n('span').addClass('user-name').text(user.name))
            )
        }
    }
}