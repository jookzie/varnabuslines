import {isNullOrEmpty} from "../utils/ValidatorExtensions";
import {getAxiosInstance} from "../authorization/AuthorizationService";
import {handleError, ResponseCode} from "../utils/ErrorHandler";
import PropTypes from "prop-types";

export function getAllAlerts() {
    const axios = getAxiosInstance();

    return axios.get('/alerts').then((response) => {
        return response.data.array;
    }).catch((error) => {
        return handleError(error);
    });
}

export function createAlert(title, content){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.post('/alerts', {
        title: title,
        content: content
    }).then((response) => {
        return response.data;
    }).catch((error) => {
        return handleError(error);
    });
}
createAlert.propTypes = {
    title: PropTypes.number.isRequired,
    content: PropTypes.number.isRequired,
}

export function updateAlert(id, title, content){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.put('/alerts/' + id, {
        title: title,
        content: content
    }).then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
updateAlert.propTypes = {
    id: PropTypes.number.isRequired,
    title: PropTypes.number.isRequired,
    content: PropTypes.number.isRequired,
}

export function deleteAlert(id){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.delete('/alerts/' + id).then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
deleteAlert.propTypes = {
    id: PropTypes.number.isRequired
}