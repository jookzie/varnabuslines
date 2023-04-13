import {isNullOrEmpty} from "../utils/ValidatorExtensions";


const CACHE_EXPIRATION_TIME = 60 * 1000; // 1 minute

class Payload {
    constructor(key, value) {
        this.key = key;
        this.value = value;
        this.expirationTime = Date.now() + CACHE_EXPIRATION_TIME;
    }
}

function isPayload(payload) {
    if(!payload){
        return false;
    }

    const realKeys = Object.getOwnPropertyNames(new Payload());
    const payloadKeys = Object.getOwnPropertyNames(payload);

    return realKeys.every(realKey => payloadKeys.includes(realKey));
}

export function cache(key, value) {
    if (isNullOrEmpty(...arguments))
        return;

    const payload = new Payload(key, value);

    sessionStorage.setItem(key, JSON.stringify(payload));
}

export function getCache(key) {
    if (isNullOrEmpty(...arguments)) {
        return null;
    }

    const payload = JSON.parse(sessionStorage.getItem(key));

    if(!isPayload(payload)){
        return null;
    }

    if(payload.expirationTime < Date.now()){
        sessionStorage.removeItem(key);
        return null;
    }
    return payload.value;
}

export function clearCache(key) {
    if (isNullOrEmpty(...arguments)) {
        return;
    }

    sessionStorage.removeItem(key);
}
