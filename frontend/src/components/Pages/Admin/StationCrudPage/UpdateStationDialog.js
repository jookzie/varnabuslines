import * as React from "react";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import PropTypes from "prop-types";
import {updateStation} from "../../../../services/station/StationService";
import ComboBox from "../../../ComboBox";

export default function UpdateStationDialog(props){
    const {open, setOpen, callback, station} = props;
    const options = ['Yes', 'No'];

    const [status, setStatus] = React.useState(station?.available ? options[0] : options[1]);

    const [errorMessage, setErrorMessage] = React.useState('');

    function handleUpdate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const name = data.get('name');
        const address = data.get('address');
        const latitude = data.get('latitude');
        const longitude = data.get('longitude');

        updateStation(station.id, name, address, latitude, longitude, status === options[0])
            .then((response) => {
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
        <SimpleDialog title='Update a station' open={open}>
            <Box component="form" noValidate onSubmit={handleUpdate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required defaultValue={station?.name} id="name" label="Name" name='name' variant="outlined" />
                <TextField required defaultValue={station?.address} id="address" label="Address" name='address' variant="outlined" />
                <TextField required defaultValue={station?.coordinates?.latitude} id="latitude" label="Latitude" name='latitude' variant="outlined" />
                <TextField required defaultValue={station?.coordinates?.longitude} id="longitude" label="Longitude" name='longitude' variant="outlined" />
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
                    <DialogButton type='submit' isDefault label='Update'/>
                    <DialogButton onClick={handleClose} label='Cancel' isDefault={false}/>
                </Box>

            </Box>
        </SimpleDialog>
    )
}
UpdateStationDialog.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    callback: PropTypes.func,
    station: PropTypes.object,
}