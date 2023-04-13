import PropTypes from "prop-types";
import {toSentenceCase} from "./StringExtensions";

export const ResponseCode = {
    NETWORK_ERROR: 1,
    INVALID_EMAIL: 2,
    BAD_PASSWORD: 3,
    EMPTY_FIELD: 4,
    SUCCESS: 200,
    BAD_REQUEST: 400,
    UNAUTHORIZED: 401,
    FORBIDDEN: 403,
    NOT_FOUND: 404,
    CONFLICT: 409,
    INTERNAL_SERVER_ERROR: 500,
}

export function handleError(error) {
    if (!error.response) {
        return ResponseCode.NETWORK_ERROR;
    }
    return error.response.status;
}

handleError.propTypes = {
    error: PropTypes.object.isRequired
}

export function getMessageFromErrorCode(code, objectName = 'Object') {

    const name = toSentenceCase(objectName);

    switch (code) {
        case ResponseCode.EMPTY_FIELD:
            return 'One or more fields are empty.';
        case ResponseCode.NETWORK_ERROR:
            return 'Server could not be reached. Please check your internet connection.';
        case ResponseCode.INVALID_EMAIL:
            return 'Email is not in the correct format.';
        case ResponseCode.BAD_PASSWORD:
            return 'Password must be at least 8 characters long.';
        case ResponseCode.BAD_REQUEST:
            return 'Bad request.';
        case ResponseCode.UNAUTHORIZED:
            window.location.href = '/authentication';
            return "You are not authorized to view this page.";
        case ResponseCode.FORBIDDEN:
            return 'Forbidden operation.';
        case ResponseCode.NOT_FOUND:
            return 'No such ' + name.toLowerCase() + ' exists with the specified details.';
        case ResponseCode.CONFLICT:
            return 'A '+ name.toLowerCase() + ' already exists with the same details.';
        case ResponseCode.INTERNAL_SERVER_ERROR:
            return 'Server error. Please try again later.';
        default:
            return null;
    }
}

getMessageFromErrorCode.propTypes = {
    code: PropTypes.number.isRequired,
    objectName: PropTypes.string
}