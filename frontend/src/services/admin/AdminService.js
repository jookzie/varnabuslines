import {getAxiosInstance} from "../authorization/AuthorizationService";
import {handleError, ResponseCode} from "../utils/ErrorHandler";
import {isNullOrEmpty, validateEmail, validatePassword} from "../utils/ValidatorExtensions";

export function createAdmin(email, password) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    if (!validateEmail(email)) {
        return Promise.resolve(ResponseCode.INVALID_EMAIL);
    }

    if (!validatePassword(password)) {
        return Promise.resolve(ResponseCode.BAD_PASSWORD);
    }

    const axios = getAxiosInstance();

    return axios.post('/admins', {
            email: email,
            password: password
        }).then(() => {
            return ResponseCode.SUCCESS;
        }).catch((error) => {
            return handleError(error);
        });
}

export function updateAsAdmin(id, email, password, role) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    if (!validateEmail(email)) {
        return Promise.resolve(ResponseCode.INVALID_EMAIL);
    }

    if (!validatePassword(password)) {
        return Promise.resolve(ResponseCode.BAD_PASSWORD);
    }

    const axios = getAxiosInstance();

    return axios.put('/admins/' + id, {
            email: email,
            password: password,
            role: role
        }).then(() => {
            return ResponseCode.SUCCESS;
        }).catch((error) => {
            return handleError(error);
        });
}