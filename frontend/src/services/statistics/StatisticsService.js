import {getAxiosInstance} from "../authorization/AuthorizationService";



export function getStationStatistics(){
    const axios = getAxiosInstance();

    return axios.get('/statistics/stations')
    .then((response) => {
        return response.data.stations;
    }).catch(() => {
        return 0;
    });
}