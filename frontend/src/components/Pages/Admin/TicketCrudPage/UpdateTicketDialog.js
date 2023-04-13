import * as React from "react";
import {updateTicket} from "../../../../services/ticket/TicketService";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import PropTypes from "prop-types";

export default function UpdateTicketDialog(props){
    const {open, setOpen, callback, ticket} = props;

    const [errorMessage, setErrorMessage] = React.useState('');

    function handleUpdate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const price = data.get('price');
        const duration = data.get('duration');

        updateTicket(ticket.id, price, duration).then((response) => {
            if(response === ResponseCode.SUCCESS){
                callback?.();
                handleClose();
            }
            else{
                setErrorMessage(getMessageFromErrorCode(response) || 'Unknown error.');
            }
        });
    }

    function handleClose() {
        setErrorMessage('');
        setOpen(false);
    }

    return (
        <SimpleDialog title='Update a ticket' open={open}>
            <Box component="form" noValidate onSubmit={handleUpdate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required id="price" label="Price (0.00€)" name='price' variant="outlined" defaultValue={ticket?.price} />
                <TextField required id="duration" label="Duration (minutes)" name='duration' variant="outlined"  defaultValue={ticket?.duration}/>
                <Typography sx={{
                    color: 'red',
                    fontSize: '0.9rem',
                    textAlign: 'center',
                    margin: 0,
                }}>
                    {errorMessage}
                </Typography>
                <Box sx={{display: 'flex', flexDirection: 'row', gap: 2}}>
                    <DialogButton type='submit' isDefault label='Update'/>
                    <DialogButton onClick={handleClose} label='Cancel' isDefault={false}/>
                </Box>

            </Box>
        </SimpleDialog>
    )
}
UpdateTicketDialog.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    callback: PropTypes.func,
    ticket: PropTypes.object,
}