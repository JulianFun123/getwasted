export default class EventBus {
    constructor() {
        /**
         * @type {Object<string, function[]>}
         */
        this.eventListeners = {}
    }

    /**
     * @param {string}  event
     * @param {function} callback
     */
    on(event, callback) {
        if (!this.eventListeners[event])
            this.eventListeners[event] = []

        this.eventListeners[event].push(callback)
    }

    /**
     * @param {string} event
     * @param {function} callback
     */
    removeListener(event, callback) {
        if (this.eventListeners[event]) {
            this.eventListeners[event] = this.eventListeners[event].filter(c => c !== callback)
        }
    }

    /**
     * @param {string} event
     * @param data
     */
    emit(event, ...data) {
        if (this.eventListeners[event]) {
            this.eventListeners[event].forEach(callback => callback.call(this, ...data))
        }
    }

}