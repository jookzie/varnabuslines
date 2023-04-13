import {isNullOrEmpty, validateEmail, validatePassword} from "../utils/ValidatorExtensions";
import {getAxiosInstance} from "../authorization/AuthorizationService";
import {handleError, ResponseCode} from "../utils/ErrorHandler";

export function getAllUsers () {
    let axios = getAxiosInstance();

    return axios.get('/users')
        .then((response) => {
            return response.data.array;
        }).catch(() => {
            return [];
        });
}

export function getUser(id) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    let axios = getAxiosInstance();

    return axios.get('/users/' + id)
        .then((response) =>{
            return response.data;
        }).catch((error) => {
            return handleError(error);
        });
}



export function createUser(email, password) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    if (!validateEmail(email)) {
        return Promise.resolve(ResponseCode.INVALID_EMAIL);
    }

    if (!validatePassword(password)) {
        return Promise.resolve(ResponseCode.BAD_PASSWORD);
    }

    let axios = getAxiosInstance();

    return axios.post(
        '/users', {
            email: email,
            password: password
        }).then(() => {
            return ResponseCode.SUCCESS;
        }).catch((error) => {
            return handleError(error);
        });
}

export function updateUser(id, email, password, role) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    let axios = getAxiosInstance();

    return axios.put('/users/' + id, {
        email: email,
        password: password,
        role: role
    }).then((response) => {
        return ResponseCode.SUCCESS;
    });
}


export function deleteUser(id) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    let axios = getAxiosInstance();

    return axios.delete('/users/' + id)
        .then(() => {
            return ResponseCode.SUCCESS;
        }).catch((error) => {
            return handleError(error);
        });
}
