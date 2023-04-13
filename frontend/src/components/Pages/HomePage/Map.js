import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
import {StationIcon} from "./Marker/StationIcon";

export default function Map(props){
    const {stations} = props;

    return (
        <MapContainer center={[43.21,27.895]} zoom={14} scrollWheelZoom={true}>
            <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                //url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                url="https://tile.thunderforest.com/transport/{z}/{x}/{y}.png?apikey=9a0a1bb040e540dfafec013c04e79f5a"
            />
            {Array.isArray(stations) && stations.map(station => (
                <Marker position={[station.coordinates.latitude, station.coordinates.longitude]}
                        icon={StationIcon}
                        key={station.id}>
                    <Popup key={`popup-${station.id}`}>
                        {station.name}
                    </Popup>
                </Marker>
            ))}
        </MapContainer>
    );
}