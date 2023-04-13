import EnhancedTable from "../../../EnhancedTable/EnhancedTable";
import {useEffect, useState} from 'react';
import CreateStationDialog from "./CreateStationDialog";
import DeleteEntityDialog from "../../../Dialog/DeleteEntityDialog";
import {ResponseCode} from "../../../../services/utils/ErrorHandler";
import UpdateStationDialog from "./UpdateStationDialog";
import {deleteStation, getAllStations} from "../../../../services/station/StationService";

export default function StationCrudPage() {
    const [stations, setStations] = useState([]);
    const [selectedStation, setSelectedStation] = useState(null);
    const [createDialogState, setCreateDialogState] = useState(false);
    const [updateDialogState, setUpdateDialogState] = useState(false);
    const [deleteDialogState, setDeleteDialogState] = useState(false);


    useEffect(() => {
        refresh();
    }, []);

    function handleDelete(id) {
        deleteStation(id).then((response) => {
            if (response === ResponseCode.SUCCESS) {
                setStations(stations.filter((station) => station.id !== id));
            }
        });
    }

    function refresh() {
        getAllStations().then((array) => {
            setStations(array);
        });
    }

    function format(value, dataLabel){
        switch(dataLabel){
            case "coordinates":
                return `Latitude: ${value?.latitude?.toFixed(4) || ''}, \nLongitude: ${value?.longitude?.toFixed(4) || ''}`;
            case "available":
                return value ? "Yes" : "No";
            default:
                return value ? value.toString() : "N/A";
        }
    }

    return (
        <>
            <EnhancedTable
                entityName='station'
                rows={stations}
                setCreateDialogState={setCreateDialogState}
                setUpdateDialogState={setUpdateDialogState}
                setDeleteDialogState={setDeleteDialogState}
                setSelectedTarget={setSelectedStation}
                format={format}
            />
            <CreateStationDialog
                open={createDialogState}
                setOpen={setCreateDialogState}
                callback={refresh}
            />
            <UpdateStationDialog
                open={updateDialogState}
                setOpen={setUpdateDialogState}
                station={selectedStation}
                callback={refresh}
            />
            <DeleteEntityDialog open={deleteDialogState}
                                entityName='station'
                                entityId={selectedStation?.id}
                                deleteFunction={handleDelete}
                                setOpen={setDeleteDialogState}
            />
        </>
    );
}
