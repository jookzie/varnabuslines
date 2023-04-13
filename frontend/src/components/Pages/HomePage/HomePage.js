import {useEffect, useReducer, useState} from "react";
import 'leaflet/dist/leaflet.css';
import BuslinesCard from "./BuslinesCard";
import Map from "./Map";
import {getAllBuslines} from "../../../services/busline/BusLineService";
import {flatMapUnique} from "../../../services/utils/ArrayExtensions";

const HomePage = () => {
    const [buslines, setBuslines] = useState([]);
    const [selectedStation, setSelectedStation] = useState(null);
    const [displayedStations, setDisplayedStations] = useState([]);

    // The array of buslines selected in the BuslinesCard component show their stations on the map
    // The toggleSelectedBuslines function takes the current array of buslines, and another array...
    // ...of buslines to for operation. If the busline is already in the array, it is removed, otherwise...
    // ...it is added to the array.
    const [selectedBuslines, toggleSelectedBuslines] = useReducer((state, buslines) => {
        if(!Array.isArray(buslines)) {
            buslines = [buslines];
        }

        let newSelectedBuslines = [];
        for(let busline of buslines) {
            if(state.some(selectedBusline => selectedBusline.id === busline.id)) {
                // If the busline is already in the array, remove it
                newSelectedBuslines = state.filter(b => b.id !== busline.id);
            } else {
                // If the busline is not in the array, add it
                newSelectedBuslines = [...state, busline];
            }
        }

        return newSelectedBuslines;
    }, [], () => []);


    useEffect(() => {
        const stations = flatMapUnique(
            selectedBuslines,
            busline => busline.stations,
            (a,b) => a.id === b.id
        );
        setDisplayedStations(stations);

    }, [selectedBuslines]);

    useEffect(() => {
        getAllBuslines().then((response) => {
            if(response && Array.isArray(response)) {
                if(selectedStation) {
                    const buslinesCrossingStation = response.filter(busline =>
                        busline.stations.some(station => station.id === selectedStation.id)
                    );
                    setBuslines(buslinesCrossingStation);
                } else {
                    setBuslines(response || []);
                }
            } else {
                setBuslines([]);
            }
        });
    }, [selectedStation]);



    function handleBuslineClick(busline) {
        toggleSelectedBuslines(busline);
    }


    return (
        <div style={{position: 'relative'}}>
            <div style={{
                position: 'absolute',
                zIndex: 10000
            }}>
                <BuslinesCard buslines={buslines}
                              onBuslineClick={handleBuslineClick}
                              selectedStation={selectedStation}
                              setSelectedStation={setSelectedStation}
                />
            </div>
            <div style={{
                position: 'absolute',
                width: '100%',
            }}>
                <Map stations={displayedStations}/>
            </div>
        </div>
    );
};
export default HomePage;
