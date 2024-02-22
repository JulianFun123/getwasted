import Page from "../Page.js";
import {$n} from "../helper.js";

export default class JoinLobby extends Page {
    async submit() {
        this.getWasted.joinLobby(this.codeInput.val(), { name: this.nameInput.val() })
    }

    render() {
        this.nameInput = $n('input')
            .addClass('input')
        this.codeInput = $n('input')
            .addClass('input')

        this.getWasted.$screen
            .html('')
            .append(
                $n('form')
                    .append(
                        $n('div')
                            .append($n('label').text('CODE'))
                            .append(this.codeInput)
                    )
                    .append(
                        $n('div')
                            .addClass('mt-4')
                            .append($n('label').text('DEIN NUTZERNAME'))
                            .append(this.nameInput)
                    )
                    .append(
                        $n('button')
                            .addClass('btn')
                            .addClass('full-width')
                            .setAttr('type', 'submit')
                            .text('Gruppe beitreten')
                            .addClass('mt-4')
                    )
                    .on('submit', e => {
                        this.submit()
                        e.preventDefault()
                    })
            )
    }
}