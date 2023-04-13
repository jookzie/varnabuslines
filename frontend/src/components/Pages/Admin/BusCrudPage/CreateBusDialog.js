import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import * as React from "react";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import {createBus} from "../../../../services/bus/BusService";
import ComboBox from "../../../ComboBox";

export default function CreateBusDialog(props) {
    const {open, setOpen, callback} = props;
    const [errorMessage, setErrorMessage] = React.useState('');
    const options = ['Yes', 'No'];
    const [status, setStatus] = React.useState(options[1]);


    function handleCreate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const latitude = data.get('latitude');
        const longitude = data.get('longitude');

        createBus(latitude, longitude, false).then((response) => {
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
        <SimpleDialog title='Create a bus' open={open}>
            <Box component="form" noValidate onSubmit={handleCreate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required id="latitude" label="Latitude" name='latitude' variant="outlined" />
                <TextField required id="longitude" label="Longitude" name='longitude' variant="outlined" />
                <ComboBox options={options} label='Active' placeholder='Active' value={status} setValue={setStatus}/>
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