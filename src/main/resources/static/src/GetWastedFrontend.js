import Homepage from "./pages/Homepage.js";
import Cajax from "../lib/cajax/Cajax.js";
import EventBus from "./EventBus.js";
import Lobby from "./pages/Lobby.js";
import GameSelection from "./pages/GameSelection.js";
import LobbySocketClient from "./LobbySocketClient.js";
import CardsAgainstHumanitySelection from "./gamemodes/cardsagainsthumanity/CardsAgainstHumanitySelection.js";
import CardsAgainstHumanity from "./gamemodes/cardsagainsthumanity/CardsAgainstHumanity.js";
import LoadingPage from "./pages/LoadingPage.js";

export default class GetWastedFrontend {
    /** @type {HTMLComponent} */
    $screen

    /** @type {LobbySocketClient} */
    socket = null

    /** @type {Cajax} */
    apiClient = new Cajax()

    /** @type {EventBus} */
    eventBus = new EventBus()

    /** @type {LobbySocketClientReceivedEventStates[keyof LobbySocketClientReceivedEventStates],null} */
    currentState = null

    /** @type {GameMode} */
    currentGame = null

    /**
     * @typedef {Object} GAMEMODE
     * @property {string} img
     * @property {Class<GameMode>,undefined} class
     * @property {Function} click
     */
    /**
     * @type {Object<string, GAMEMODE>}
     */
    gameModes = {}


    /**
     * @param {HTMLComponent} $screen
     */
    constructor($screen) {
        this.$screen = $screen

        this.apiClient.promiseInterceptor = r => r.json()

        this.gameModes = {
            CARDS_AGAINST_HUMANITY: {
                img: 'assets/cards-against-humanity.svg',
                class: CardsAgainstHumanity,
                click: () => this.setScreen(new CardsAgainstHumanitySelection(this))
            },
            HUNDRED_QUESTIONS: {
                img: 'assets/hundred-questions.svg',
                click: () => this.setScreen(new CardsAgainstHumanitySelection(this))
            },
            CITY_COUNTRY_RIVER: {
                img: 'assets/city-country-river.svg',
                click: () => this.setScreen(new CardsAgainstHumanitySelection(this))
            },
            WOULD_YOU_RATHER: {
                img: 'assets/would-you-rather.svg',
                click: () => this.setScreen(new CardsAgainstHumanitySelection(this))
            }
        }
    }

    /**
     * @param {Page} screen
     */
    setScreen(screen) {
        if (this.currentScreen) {
            this.eventBus.removeListener('stateUpdate', this.currentScreen.events.onStateUpdate)
            this.eventBus.removeListener('stateChange', this.currentScreen.events.onStateChange)
            this.eventBus.removeListener('data', this.currentScreen.events.onData)

            this.currentScreen.destroy()
        }
        this.currentScreen = screen

        this.currentScreen.events = {
            onStateUpdate: d => this.currentScreen.onStateUpdate(d),
            onStateChange: d => this.currentScreen.onStateChange(d),
            onData: d => this.currentScreen.onData(d)
        }

        this.eventBus.on('stateUpdate', this.currentScreen.events.onStateUpdate)
        this.eventBus.on('stateChange', this.currentScreen.events.onStateChange)
        this.eventBus.on('data', this.currentScreen.events.onData)

        this.currentScreen.init()
        this.render()
        this.currentScreen.initialized()
    }

    render() {
        this.currentScreen.render()
    }

    run() {
        this.setScreen(new Homepage(this))

        if (window.location.hash === '#play') {
            this.joinLastGame()
        }
    }

    async joinLobby(code, data) {
        this.socket = new LobbySocketClient(code, data)

        let lastStateId = -1
        let hadFirstResponse = false

        this.socket.on('receivedaa', e => {

        })

        this.socket.on('received', data => {


            this.eventBus.emit('data', data)

            this.currentState = data

            this.eventBus.emit('stateUpdate', data)

            if (!hadFirstResponse) {
                localStorage['last_session'] = JSON.stringify({
                    code,
                    userKey: data.current_user.user_key
                })
                hadFirstResponse = true
            }

            if (lastStateId !== data.state_id) {
                this.eventBus.emit('stateChanged', data)

                if (this.currentGame) {
                    this.currentGame.stop()
                }

                if (data.state === 'AWAITING') {
                    this.setScreen(new Lobby(this))
                } else if (data.state === 'SELECTING') {
                    this.setScreen(new GameSelection(this))
                } else if (data.state === 'INGAME') {
                    this.setScreen(new LoadingPage(this))

                    this.currentGame = new this.gameModes[data.game_type].class(this)
                    this.currentGame.start()
                }
            }

            lastStateId = data.state_id
        })

        await this.socket.connect()
        window.location.hash = '#play'
    }

    async joinLastGame() {
        if (localStorage['last_session']) {
            const lastSession = JSON.parse(localStorage['last_session'])
            await this.joinLobby(lastSession.code, {user_key: lastSession.userKey})
        }
    }
}