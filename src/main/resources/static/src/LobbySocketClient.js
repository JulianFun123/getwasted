import WebSocketClient from "./WebSocketClient.js";

/**
 * @typedef User
 * @property {string} id
 * @property {string} name
 */

/**
 * @typedef {User} CurrentUser
 * @property {string} user_key
 * @property {'ADMIN' | 'PLAYER' | 'VIEWER'} type
 */

/**
 * @typedef CurrentGameState
 * @property {string} state
 * @property {string} state_id
 * @property {Object} data
 */

/**
 * @typedef CurrentLobbyState
 * @property {'AWAITING' | 'SELECTING' | 'INGAME' | string} state
 * @property {string} code
 * @property {string} state_id
 * @property {CurrentGameState,undefined} game
 * @property {string[]} game_modes
 * @property {User[]} users
 * @property {CurrentUser} current_user
 */

/**
 * @typedef {CurrentLobbyState} LobbySocketClientReceivedIngameEvent
 * @property {string} game_type
 */

/**
 * @typedef LobbySocketClientReceivedEventStates
 * @property {LobbySocketClientReceivedIngameEvent} INGAME
 * @property {CurrentLobbyState} AWAITING
 * @property {CurrentLobbyState} SELECTING
 */

/**
 * @typedef LobbySocketClientErrorEvent
 * @property {Event} error
 */

/**
 * @typedef LobbySocketClientEvents
 * @property {LobbySocketClientReceivedEventStates[keyof LobbySocketClientReceivedEventStates]} received
 * @property {LobbySocketClientErrorEvent} error
 */

export default class LobbySocketClient extends WebSocketClient {
    constructor(code, data) {
        super(`ws://${window.location.host}/lobby/${code}`, data);
    }


    /**
     * @param {string} type
     * @param {*} data
     */
    async action(type, data = {}) {
        return await this.send({
            ...data,
            type
        })
    }

    /**
     * @param {string} game
     * @param {*} settings
     */
    async startGame(game, settings) {
        return this.action('gamemodes.StartGameRequest', {
            game,
            settings
        })
    }

    /**
     * @template {keyof LobbySocketClientEvents} K
     * @param {K,string} event
     * @param {function(LobbySocketClientEvents[K]),function} callback
     */
    on(event, callback) {
        super.on(event, callback);
    }
}