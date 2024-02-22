export default class Page {
    /**
     * @param {GetWastedFrontend} getWasted
     */
    constructor(getWasted) {
        this.getWasted = getWasted

        this.events = {
            onStateUpdate: null,
            onStateChange: null,
            onData: null
        }
    }

    init() {}
    initialized() {}
    render() {}
    destroy() {}


    /**
     * @param {LobbySocketClientReceivedEventStates[keyof LobbySocketClientReceivedEventStates]} data
     */
    onStateUpdate(data) {}
    /**
     * @param {LobbySocketClientReceivedEventStates[keyof LobbySocketClientReceivedEventStates]} data
     */
    onStateChange(data) {}

    /**
     * @param {LobbySocketClientEvents[keyof LobbySocketClientEvents]} data
     */
    onData(data) {}
}