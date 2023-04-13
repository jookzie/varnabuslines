import {getAxiosInstance} from "../authorization/AuthorizationService";
import {handleError, ResponseCode} from "../utils/ErrorHandler";
import PropTypes from "prop-types";

export function getAllBuslines() {
    const axios = getAxiosInstance();

    return axios.get('/buslines').then((response) => {
        return response.data.array.sort((a, b) => +(a.id > b.id) || -(a.id < b.id));
    }).catch(() => {
        return [];
    });
}

export function createBusline(id) {
    const axios = getAxiosInstance();

    return axios.post('/buslines', {id: id || null})
    .then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
createBusline.propTypes = {
    id: PropTypes.number.isRequired
}


export function updateBuslineBuses(buslineId, busIds) {
    const axios = getAxiosInstance();

    return axios.put(`/buslines/${buslineId}/buses`, {busIds: busIds})
    .then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}


export function updateBuslineTickets(buslineId, ticketIds) {
    const axios = getAxiosInstance();

    return axios.put(`/buslines/${buslineId}/tickets`, {ticketIds: ticketIds})
    .then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}

export function updateBuslineRoute(buslineId, stationIds) {
    const axios = getAxiosInstance();

    return axios.put(`/buslines/${buslineId}/route`, {stationIds: stationIds})
    .then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}


export function deleteBusline(id) {
    const axios = getAxiosInstance();

    return axios.delete('/buslines/' + id)
    .then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}