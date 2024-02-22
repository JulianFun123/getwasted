import JDOM from "../lib/jdom/JDOM.js";


/**
 * @typedef {JDOM} Die
 */
/**
 * @param s
 * @returns {HTMLComponent}
 */
export const $ = s => new JDOM(s)
/**
 * @type {(function(string=): HTMLComponent)|*}
 */
export const $n = JDOM.new