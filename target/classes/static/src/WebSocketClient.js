import EventBus from "./EventBus.js";

export default class WebSocketClient extends EventBus {
    /**
     * @param {string} address
     * @param data
     */
    constructor(address, data) {
        super();
        const query = Object.keys(data).map(key => `${key}=${data[key]}`).join('&')

        this.address = `${address}?${query}`;
    }

    async send(data) {
        await this.connect()

        this.socket.send(JSON.stringify(data))
    }

    connect() {
        return new Promise((res, err) => {
            if (this.socket) {
                res()
                return;
            }

            this.socket = new WebSocket(this.address)
            let resolved = false
            this.socket.addEventListener('open', () => {
                res()
                resolved = true
            })

            this.socket.addEventListener('close', () => {
                this.socket = null
                if (!resolved) {
                    err()
                }
            })

            this.socket.addEventListener('error', e => {
                this.emit('error', e)
            })

            this.socket.addEventListener('message', e => {
                this.emit('received', JSON.parse(e.data))
            })
        })
    }
}