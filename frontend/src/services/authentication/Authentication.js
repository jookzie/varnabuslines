import {isNullOrEmpty, validateEmail} from "../utils/ValidatorExtensions";
import {getAxiosInstance, removeToken, setToken} from "../authorization/AuthorizationService";
import {handleError, ResponseCode} from "../utils/ErrorHandler";


export function login(email, password) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    if (!validateEmail(email)) {
        return Promise.resolve(ResponseCode.INVALID_EMAIL);
    }
    let axios = getAxiosInstance();

    return axios.post('/login', {
            email: email,
            password: password
    }).then((response) => {
        setToken(response.data.token);
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}

export const logout = () => {
    removeToken();
}


