import * as React from "react";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import PropTypes from "prop-types";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import {createBusline} from "../../../../services/busline/BusLineService";
import TextField from "@mui/material/TextField";

export default function CreateBusLineDialog(props){
    const {open, setOpen, callback} = props;

    const [errorMessage, setErrorMessage] = React.useState('');

    function handleCreate(event){
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const id = data.get('id');
        createBusline(id)
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
        <SimpleDialog title='Create a bus line' open={open}>
            <Box component="form" noValidate onSubmit={handleCreate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
             }}>
                <TextField required id="id" label="Identifier" name='id' variant="outlined" />
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
CreateBusLineDialog.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    callback: PropTypes.func,
    busLine: PropTypes.object,
}