import {useEffect, useState} from "react";
import {deleteBus, getAllBuses} from "../../../../services/bus/BusService";
import {ResponseCode} from "../../../../services/utils/ErrorHandler";
import EnhancedTable from "../../../EnhancedTable/EnhancedTable";
import CreateBusDialog from "../BusCrudPage/CreateBusDialog";
import UpdateBusDialog from "../BusCrudPage/UpdateBusDialog";
import DeleteEntityDialog from "../../../Dialog/DeleteEntityDialog";
import {isNullOrEmpty} from "../../../../services/utils/ValidatorExtensions";

export default function BusCrudPage(){
    const [buses, setBuses] = useState([]);
    const [selectedBus, setSelectedBus] = useState(null);
    const [createDialogState, setCreateDialogState] = useState(false);
    const [updateDialogState, setUpdateDialogState] = useState(false);
    const [deleteDialogState, setDeleteDialogState] = useState(false);


    useEffect(() => {
        refresh();
    }, []);

    function handleDelete(id) {
        deleteBus(id).then((response) => {
            if (response === ResponseCode.SUCCESS) {
                setBuses(buses.filter((bus) => bus.id !== id));
            }
        });
    }

    function refresh() {
        getAllBuses().then((array) => {
            setBuses(array);
        });
    }

    function format(value, dataLabel){
        switch(dataLabel){
            case "coordinates":
                return `Latitude: ${value?.latitude.toFixed(4)}, \nLongitude: ${value?.longitude.toFixed(4)}`;
            case "available":
                return value ? "Yes" : "No";
            case "id":
            default:
                return !isNullOrEmpty(value) ? value : "N/A";
        }
    }

    return (
        <>
            <EnhancedTable
                entityName='bus'
                rows={buses}
                setCreateDialogState={setCreateDialogState}
                setUpdateDialogState={setUpdateDialogState}
                setDeleteDialogState={setDeleteDialogState}
                setSelectedTarget={setSelectedBus}
                format={format}
            />
            <CreateBusDialog
                open={createDialogState}
                setOpen={setCreateDialogState}
                callback={refresh}
            />
            <UpdateBusDialog
                open={updateDialogState}
                setOpen={setUpdateDialogState}
                bus={selectedBus}
                callback={refresh}
            />
            <DeleteEntityDialog open={deleteDialogState}
                                entityName='bus'
                                entityId={selectedBus?.id}
                                deleteFunction={handleDelete}
                                setOpen={setDeleteDialogState}
            />
        </>
    );
}