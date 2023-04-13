import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import * as React from "react";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import {createStation} from "../../../../services/station/StationService";

export default function CreateStationDialog(props) {
    const {open, setOpen, callback} = props;
    const [errorMessage, setErrorMessage] = React.useState('');

    function handleCreate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const name = data.get('name');
        const address = data.get('address');
        const latitude = data.get('latitude');
        const longitude = data.get('longitude');

        createStation(name, address, latitude, longitude).then((response) => {
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
        <SimpleDialog title='Create a station' open={open}>
            <Box component="form" noValidate onSubmit={handleCreate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required id="name" label="Name" name='name' variant="outlined" />
                <TextField required id="address" label="Address" name='address' variant="outlined" />
                <TextField required id="latitude" label="Latitude" name='latitude' variant="outlined" />
                <TextField required id="longitude" label="Longitude" name='longitude' variant="outlined" />
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