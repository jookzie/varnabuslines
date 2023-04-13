import {getAxiosInstance} from "../authorization/AuthorizationService";
import {handleError, ResponseCode} from "../utils/ErrorHandler";
import {isNullOrEmpty} from "../utils/ValidatorExtensions";
import PropTypes from "prop-types";


export function getAllTickets() {
    const axios = getAxiosInstance();

    return axios.get('/tickets')
    .then((response) => {
        return response.data.array;
    }).catch((error) => {
        return handleError(error);
    });
}

export function createTicket(price, duration){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.post('/tickets', {
        price: price,
        duration: duration
    }).then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
createTicket.propTypes = {
    price: PropTypes.number.isRequired,
    duration: PropTypes.number.isRequired
}

export function updateTicket(id, price, duration){
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.put('/tickets/' + id, {
        price: price,
        duration: duration
    }).then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
updateTicket.propTypes = {
    id: PropTypes.number.isRequired,
    price: PropTypes.number.isRequired,
    duration: PropTypes.number.isRequired,
}

export function deleteTicket(id) {
    if(isNullOrEmpty(...arguments)){
        return Promise.resolve(ResponseCode.EMPTY_FIELD);
    }

    const axios = getAxiosInstance();

    return axios.delete('/tickets/' + id)
    .then(() => {
        return ResponseCode.SUCCESS;
    }).catch((error) => {
        return handleError(error);
    });
}
deleteTicket.propTypes = {
    id: PropTypes.number.isRequired,
}
