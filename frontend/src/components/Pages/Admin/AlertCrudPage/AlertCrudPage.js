import {useEffect, useState} from "react";
import {deleteAlert, getAllAlerts} from "../../../../services/alert/AlertService";
import {ResponseCode} from "../../../../services/utils/ErrorHandler";
import EnhancedTable from "../../../EnhancedTable/EnhancedTable";
import CreateAlertDialog from "../AlertCrudPage/CreateAlertDialog";
import UpdateAlertDialog from "../AlertCrudPage/UpdateAlertDialog";
import DeleteEntityDialog from "../../../Dialog/DeleteEntityDialog";
import {isNullOrEmpty} from "../../../../services/utils/ValidatorExtensions";

export default function AlertCrudPage(){
    const [alerts, setAlerts] = useState([]);
    const [selectedAlert, setSelectedAlert] = useState(null);
    const [createDialogState, setCreateDialogState] = useState(false);
    const [updateDialogState, setUpdateDialogState] = useState(false);
    const [deleteDialogState, setDeleteDialogState] = useState(false);


    useEffect(() => {
        refresh();
    }, []);

    function handleDelete(id) {
        deleteAlert(id).then((response) => {
            if (response === ResponseCode.SUCCESS) {
                setAlerts(alerts.filter((alert) => alert.id !== id));
            }
        });
    }

    function refresh() {
        getAllAlerts().then((array) => {
            setAlerts(array);
        });
    }

    function format(value, dataLabel){
        switch(dataLabel){
            case "timestamp":
                return new Date(value).toString();
            case "title":
            case "content":
            case "id":
            default:
                return !isNullOrEmpty(value) ? value : "N/A";
        }
    }

    return (
        <>
            <EnhancedTable
                entityName='alert'
                rows={alerts}
                setCreateDialogState={setCreateDialogState}
                setUpdateDialogState={setUpdateDialogState}
                setDeleteDialogState={setDeleteDialogState}
                setSelectedTarget={setSelectedAlert}
                format={format}
            />
            <CreateAlertDialog
                open={createDialogState}
                setOpen={setCreateDialogState}
                callback={refresh}
            />
            <UpdateAlertDialog
                open={updateDialogState}
                setOpen={setUpdateDialogState}
                alert={selectedAlert}
                callback={refresh}
            />
            <DeleteEntityDialog open={deleteDialogState}
                                entityName='alert'
                                entityId={selectedAlert?.id}
                                deleteFunction={handleDelete}
                                setOpen={setDeleteDialogState}
            />
        </>
    );
}