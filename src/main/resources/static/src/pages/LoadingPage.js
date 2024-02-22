import Page from "../Page.js";
import {$n} from "../helper.js";

export default class LoadingPage extends Page {
    render() {
        this.userList = $n('div')

        this.getWasted.$screen
            .html('Loading...')
    }
}