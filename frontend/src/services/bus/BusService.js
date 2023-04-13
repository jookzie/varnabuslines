import {isNullOrEmpty} from "../utils/ValidatorExtensions";
import {getAxiosInstance} from "../authorization/AuthorizationService";
import {handleError, ResponseCode} from "../utils/ErrorHandler";
import PropTypes from "prop-types";

export function getAllBuses() {
    const axios = getAxiosInstance();

    return axios.get('/buses').then((response) => {
        return response.data.array;
    }).catch((error) => {
        return handleError(error);
    });
}

export function createBus(latitude, longitude, status){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.post('/buses', {
        coordinates: {
            latitude: latitude,
            longitude: longitude
        },
        available: status
    }).then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
createBus.propTypes = {
    latitude: PropTypes.number.isRequired,
    longitude: PropTypes.number.isRequired,
    status: PropTypes.bool.isRequired
}

export function updateBus(id, latitude, longitude, status){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.put('/buses/' + id, {
        coordinates: {
            latitude: latitude,
            longitude: longitude
        },
        available: status
    }).then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
updateBus.propTypes = {
    id: PropTypes.number.isRequired,
    latitude: PropTypes.number.isRequired,
    longitude: PropTypes.number.isRequired,
    status: PropTypes.bool.isRequired
}

export function deleteBus(id){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.delete('/buses/' + id).then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
deleteBus.propTypes = {
    id: PropTypes.number.isRequired
}