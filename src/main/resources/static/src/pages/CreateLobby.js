import Page from "../Page.js";
import {$n} from "../helper.js";

export default class CreateLobby extends Page {
    async submit() {
        const { lobby_code: lobbyCode, user_key: userKey } = await this.getWasted.apiClient.post('/lobby', {
            username: this.nameInput.val()
        })

        await this.getWasted.joinLobby(lobbyCode, { user_key: userKey })
    }

    render() {
        this.nameInput = $n('input')
            .addClass('input')

        this.getWasted.$screen
            .html('')
            .append(
                $n('form')
                    .append(
                        $n('div')
                            .append($n('label').text('DEIN NUTZERNAME'))
                            .append(this.nameInput)
                    )
                    .append(
                        $n('button')
                            .addClass('btn')
                            .addClass('full-width')
                            .setAttr('type', 'submit')
                            .text('Gruppe erstellen')
                            .addClass('mt-4')
                    )
                    .on('submit', e => {
                        this.submit()
                        e.preventDefault()
                    })
            )
    }
}