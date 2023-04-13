import {handleError, ResponseCode} from "../utils/ErrorHandler";
import {getAxiosInstance} from "../authorization/AuthorizationService";
import PropTypes from "prop-types";
import {isNullOrEmpty} from "../utils/ValidatorExtensions";
import {cache, clearCache, getCache} from "../caching/CacheService";

export function getAllStations() {
    const axios = getAxiosInstance();

    const data = getCache('stations');
    if(data !== null) {
        return Promise.resolve(data);
    }

    return axios.get('/stations').then((response) => {
        cache('stations', response.data.array);
        return response.data.array;
    }).catch((error) => {
        return handleError(error);
    });
}

export function createStation(name, address, latitude, longitude) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    const body = {
        name: name,
        address: address,
        coordinates: {
            latitude: latitude,
            longitude: longitude
        }
    }

    return axios.post('/stations', body).then(() => {
        clearCache('stations');
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
createStation.propTypes = {
    name: PropTypes.string.isRequired,
    address: PropTypes.string.isRequired,
    latitude: PropTypes.number.isRequired,
    longitude: PropTypes.number.isRequired
}

export function updateStation(id, name, address, latitude, longitude, status) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.put('/stations/' + id, {
        name: name,
        address: address,
        coordinates: {
            latitude: latitude,
            longitude: longitude
        },
        available: status
    }).then(() => {
        clearCache('stations');
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
updateStation.propTypes = {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    address: PropTypes.string.isRequired,
    latitude: PropTypes.number.isRequired,
    longitude: PropTypes.number.isRequired
}

export function deleteStation(id) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.delete('/stations/' + id).then(() => {
        clearCache('stations');
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
deleteStation.propTypes = {
    id: PropTypes.number.isRequired
}