import * as React from "react";
import {useEffect, useReducer, useState} from "react";
import {ResponseCode} from "../../../../services/utils/ErrorHandler";
import EnhancedTable from "../../../EnhancedTable/EnhancedTable";
import CreateBusLineDialog from "../BusLineCrudPage/CreateBusLineDialog";
import UpdateBusLineDialog from "./UpdateBusLineDialog";
import DeleteEntityDialog from "../../../Dialog/DeleteEntityDialog";
import {
    deleteBusline,
    getAllBuslines,
    updateBuslineBuses,
    updateBuslineRoute,
    updateBuslineTickets
} from "../../../../services/busline/BusLineService";
import CustomButton from "../../../CustomButton";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import Box from "@mui/material/Box";
import {getAllBuses} from "../../../../services/bus/BusService";
import {getAllTickets} from "../../../../services/ticket/TicketService";
import {isNullOrEmpty} from "../../../../services/utils/ValidatorExtensions";
import {getAllStations} from "../../../../services/station/StationService";

export default function BusLineCrudPage(){
    const [busLines, setBusLines] = useState([]);
    const [selectedBusLine, setSelectedBusLine] = useState(null);
    const [createDialogState, setCreateDialogState] = useState(false);
    const [deleteDialogState, setDeleteDialogState] = useState(false);
    const [updateDialogState, setUpdateDialogState] = useState(false);


    const [subject, updateSubject] = useReducer((state, subject) => {
        switch (subject) {
            case 'bus':
                return {
                    buslineProp: 'buses',
                    getAllFunc: getAllBuses,
                    updateFunc: updateBuslineBuses
                }
            case 'ticket':
                return {
                    buslineProp: 'tickets',
                    getAllFunc: getAllTickets,
                    updateFunc: updateBuslineTickets
                }
            case 'route':
                return {
                    buslineProp: 'stations',
                    getAllFunc: getAllStations,
                    updateFunc: updateBuslineRoute
                }
            default:
                return {}
        }
    }, null);

    useEffect(() => {
        refresh();
    }, []);

    function handleDelete(id) {
        deleteBusline(id).then((response) => {
            if (response === ResponseCode.SUCCESS) {
                setBusLines(busLines.filter((busLine) => busLine.id !== id));
            }
        });
    }

    function refresh() {
        getAllBuslines().then((response) => {
            if (response && Array.isArray(response)) {
                setBusLines(response);
            }
        });
    }

    function handleEditBuses(busline) {
        setSelectedBusLine(busline);
        updateSubject('bus');
        setUpdateDialogState(true);
    }


    function handleEditTickets(busline) {
        setSelectedBusLine(busline);
        updateSubject('ticket');
        setUpdateDialogState(true);
    }


    function handleEditStations(busline) {
        setSelectedBusLine(busline);
        updateSubject('route');
        setUpdateDialogState(true);
    }

    function format(value, dataLabel, busline){
        const editButton = (v, onClick) => (
            <Box sx={{display: 'flex'}}>
                <CustomButton invertColors={true}
                              onClick={onClick}
                              sx={{px: 1, py: 0.2, m: 0, mr: 1, minHeight: 0, minWidth: 0, w: 1, h: 1}}
                >
                    <EditOutlinedIcon sx={{fontSize: 20, my: 0.1}}/>
                </CustomButton>
                {v}
            </Box>
        );
        let result;
        switch(dataLabel){
            case 'buses':
                if(value && value?.length > 0) {
                    result = value?.map((bus) => bus.id).join(', ');
                } else {
                    result = 'No added buses';
                }
                return editButton(result, () => handleEditBuses(busline));
            case 'stations':
                if(value && value?.length > 0) {
                    result = value?.map((station) => station.id).join(', ');
                } else {
                    result = 'No added stations';
                }
                return editButton(result, () => handleEditStations(busline));

            case 'tickets':
                if(value && value?.length > 0) {
                    result = value?.map((ticket) => ticket.id).join(', ');
                } else {
                    result = 'No added tickets';
                }
                return editButton(result, () => handleEditTickets(busline));
            case 'available':
                return value ? 'Yes' : 'No';
            case 'id':
            default:
                return !isNullOrEmpty(value) ? value : "N/A";
        }
    }

    return (
        <>
            <EnhancedTable
                entityName='Bus Line'
                rows={busLines}
                setCreateDialogState={setCreateDialogState}
                setUpdateDialogState={() => {}}
                setDeleteDialogState={setDeleteDialogState}
                setSelectedTarget={setSelectedBusLine}
                format={format}
            />
            <CreateBusLineDialog
                open={createDialogState}
                setOpen={setCreateDialogState}
                callback={refresh}
            />
            <DeleteEntityDialog open={deleteDialogState}
                                entityName='Bus Line'
                                entityId={selectedBusLine?.id}
                                deleteFunction={handleDelete}
                                setOpen={setDeleteDialogState}
            />
            <UpdateBusLineDialog open={updateDialogState}
                                 setOpen={setUpdateDialogState}
                                 busline={selectedBusLine}
                                 subject={subject}
                                 callback={refresh}
            />
        </>
    );
}