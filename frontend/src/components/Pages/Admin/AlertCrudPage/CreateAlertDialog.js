import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import * as React from "react";
import {useContext} from "react";
import {getMessageFromErrorCode} from "../../../../services/utils/ErrorHandler";
import {createAlert} from "../../../../services/alert/AlertService";
import {AlertsContext} from "../../../Root";

export default function CreateAlertDialog(props) {
    const {open, setOpen, callback} = props;
    const [errorMessage, setErrorMessage] = React.useState('');
    const stompClient = useContext(AlertsContext);

    function handleCreate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const title = data.get('title');
        const content = data.get('content');

        createAlert(title, content).then((response) => {
            if(typeof response === 'object'){
                stompClient.send("/app/alerts", {}, JSON.stringify({id: response.id }));
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
        <SimpleDialog title='Create an alert' open={open}>
            <Box component="form" noValidate onSubmit={handleCreate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required id="title" label="Title" name='title' variant="outlined" />
                <TextField required id="content" label="Content" name='content' variant="outlined" width='fit-content'/>
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