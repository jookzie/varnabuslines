import EnhancedTable from "../../../EnhancedTable/EnhancedTable";
import {useEffect, useState} from 'react';
import CreateTicketDialog from "./CreateTicketDialog";
import DeleteEntityDialog from "../../../Dialog/DeleteEntityDialog";
import {deleteTicket, getAllTickets} from "../../../../services/ticket/TicketService";
import {ResponseCode} from "../../../../services/utils/ErrorHandler";
import UpdateTicketDialog from "./UpdateTicketDialog";
import {isNullOrEmpty} from "../../../../services/utils/ValidatorExtensions";

const TicketsCrudPage = () => {
    const [tickets, setTickets] = useState([]);
    const [selectedTicket, setSelectedTicket] = useState(null);
    const [createDialogState, setCreateDialogState] = useState(false);
    const [updateDialogState, setUpdateDialogState] = useState(false);
    const [deleteDialogState, setDeleteDialogState] = useState(false);


    useEffect(() => {
        refresh();
    }, []);

    function handleDelete(id) {
        deleteTicket(id).then((response) => {
            if (response === ResponseCode.SUCCESS) {
                setTickets(tickets.filter((ticket) => ticket.id !== id));
            }
        });
    }

    function refresh() {
        getAllTickets().then((array) => {
            setTickets(array);
        });
    }

    function format(value, dataLabel) {
        switch(dataLabel) {
            case 'price':
                return value.toFixed(2) + ' €';
            case 'duration':
                return value + ' min';
            case 'id':
            default:
                return !isNullOrEmpty(value) ? value : "N/A";
        }
    }




    return (
        <>
            <EnhancedTable
                entityName='ticket'
                rows={tickets}
                setCreateDialogState={setCreateDialogState}
                setUpdateDialogState={setUpdateDialogState}
                setDeleteDialogState={setDeleteDialogState}
                setSelectedTarget={setSelectedTicket}
                format={format}
            />
            <CreateTicketDialog
                open={createDialogState}
                setOpen={setCreateDialogState}
                callback={refresh}
            />
            <UpdateTicketDialog
                open={updateDialogState}
                setOpen={setUpdateDialogState}
                ticket={selectedTicket}
                callback={refresh}
            />
            <DeleteEntityDialog open={deleteDialogState}
                                entityName='ticket'
                                entityId={selectedTicket?.id}
                                deleteFunction={handleDelete}
                                setOpen={setDeleteDialogState}
            />
        </>
    );
}
export default TicketsCrudPage;
