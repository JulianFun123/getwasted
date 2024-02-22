export default class GameMode {
    /**
     * @type {GetWastedFrontend} getWasted
     */
    getWasted

    /**
     * @param {GetWastedFrontend} getWasted
     */
    constructor(getWasted) {
        this.getWasted = getWasted
    }

    start() {}
    stop() {}
}