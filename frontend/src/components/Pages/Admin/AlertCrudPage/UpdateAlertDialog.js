import * as React from "react";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import PropTypes from "prop-types";
import {updateAlert} from "../../../../services/alert/AlertService";

export default function UpdateAlertDialog(props){
    const {open, setOpen, callback, alert} = props;
    const [errorMessage, setErrorMessage] = React.useState('');

    function handleUpdate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const title = data.get('title');
        const content = data.get('content');

        updateAlert(alert.id, title, content).then((response) => {
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
        <SimpleDialog title='Update an alert' open={open}>
            <Box component="form" noValidate onSubmit={handleUpdate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required defaultValue={alert?.title} id="title" label="Title" name='title' variant="outlined" />
                <TextField required defaultValue={alert?.content} id="content" label="Content" name='content' variant="outlined" width='fit-content'/>
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
UpdateAlertDialog.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    callback: PropTypes.func,
    alert: PropTypes.object,
}