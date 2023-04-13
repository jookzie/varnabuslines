import {parseJwt} from "../utils/JwtService";
import {getUser} from "../user/UserService";
import axios from "axios";
import Configuration from "../../local/Configuration";

export function getAxiosInstance() {
    const headers = {
        'Access-Control-Allow-Origin': "*",
    };

    const token = getToken();
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }

    return axios.create({
        baseURL: Configuration.domain,
        timeout: 5000,
        headers: headers
    });
}

export function tokenIsExpired(token) {
    if(!token)
        return null;
    const parsedToken = parseJwt(token);
    return parsedToken.exp < Date.now() / 1000;
}


export function getParsedToken() {
    const token = getToken();
    return token && parseJwt(token);
}

export function getToken() {
    const token = localStorage.getItem('token');
    if (tokenIsExpired(token)) {
        removeToken();
        return null;
    }
    return token;
}

export function setToken(token) {
    localStorage.setItem('token', token);
    window.dispatchEvent(new Event('tokenChanged'));
}

export function removeToken() {
    localStorage.removeItem('token');
    window.dispatchEvent(new Event('tokenChanged'));
}

export function getAuthUser() {
    const token = getParsedToken();
    if (!token)
        return Promise.resolve(null);

    const userId = token.userId;
    return getUser(userId);
}

export const Roles = {
    USER: 'USER',
    ADMIN: 'ADMIN'
}