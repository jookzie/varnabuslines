import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import * as React from "react";
import {createTicket} from "../../../../services/ticket/TicketService";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";

export default function CreateTicketDialog(props) {
    const {open, setOpen, callback} = props;
    const [errorMessage, setErrorMessage] = React.useState('');

    function handleCreate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const price = data.get('price');
        const duration = data.get('duration');

        createTicket(price, duration).then((response) => {
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
        <SimpleDialog title='Create a ticket' open={open}>
            <Box component="form" noValidate onSubmit={handleCreate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required id="price" label="Price (0.00€)" name='price' variant="outlined" />
                <TextField required id="duration" label="Duration (minutes)" name='duration' variant="outlined" />
                <Typography sx={{
                    color: 'red',
                    fontSize: '0.9rem',
                    textAlign: 'center',
                    margin: 0,
                }}>
                    {errorMessage}
                </Typography>
                <Box sx={{display: 'flex', flexDirection: 'row', gap: 2}}>
                    <DialogButton type='submit' isDefault label='Create'/>
                    <DialogButton onClick={handleClose} label='Cancel' isDefault={false}/>
                </Box>

            </Box>
        </SimpleDialog>
    )
}