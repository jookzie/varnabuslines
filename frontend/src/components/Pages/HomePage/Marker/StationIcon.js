import Leaflet from 'leaflet';

export const StationIcon = new Leaflet.Icon({
    iconUrl: require('./icon.png'),
    iconSize: [20, 30],
    iconAnchor: [10, 30],
    popupAnchor: [0, -30],
});