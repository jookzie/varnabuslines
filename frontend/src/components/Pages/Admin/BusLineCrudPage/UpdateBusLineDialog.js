import * as React from "react";
import {useEffect} from "react";
import SimpleDialog from "../../../Dialog/SimpleDialog";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import PropTypes from "prop-types";
import TransferList from "../../../TransferList";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import {toSentenceCase} from "../../../../services/utils/StringExtensions";


export default function UpdateBusLineDialog(props){
    const {open, setOpen, callback, busline, subject} = props;

    const [errorMessage, setErrorMessage] = React.useState('');
    const [excluded, setExcluded] = React.useState([]);
    const [included, setIncluded] = React.useState(busline?.[subject?.buslineProp] || []);


    useEffect(() => {
        if(!busline)
            return;

        setIncluded(busline?.[subject?.buslineProp] || []);
    }, [busline, subject?.buslineProp]);
    
    useEffect(() => {
        if(!busline)
            return;

        subject?.getAllFunc(busline.id).then((response) => {
            if(response && Array.isArray(response)){
                const entities = response.filter((entity) => !(included.map(inclEntity => inclEntity.id)).includes(entity.id));
                setExcluded(entities);
            }
        });
    }, [subject, included, busline]);

    function handleUpdate(){
        subject?.updateFunc(busline.id, included.map(entity => entity.id) || []).then((response) => {
            if(response === ResponseCode.SUCCESS){
                callback?.();
                handleClose();
            } else if (response === ResponseCode.CONFLICT){
                setErrorMessage(`${toSentenceCase(subject.buslineProp)} are used in another bus line.`);
            } else{
                setErrorMessage(getMessageFromErrorCode(response) || 'Unknown error.');
            }
        });
    }

    function handleClose() {
        setErrorMessage('');
        setOpen(false);
    }



    return (
        <SimpleDialog
            title={`Update ${subject?.buslineProp.toLowerCase()}`}
            open={open}>
            <Box sx={{
                display: 'flex',
                flexDirection: 'column',
                gap: 1,
                minWidth: 800,
            }}>
                <TransferList excluded={excluded}
                              setExcluded={setExcluded}
                              included={included}
                              setIncluded={setIncluded}
                              propertyToDisplay='name'
                              orderByProperty='id'
                />

                <Typography sx={{
                    color: 'red',
                    fontSize: '0.9rem',
                    textAlign: 'center',
                    margin: 0,
                }}>
                    {errorMessage}
                </Typography>
                <Box sx={{display: 'flex', justifyContent: 'flex-end', gap: 1}}>
                    <Box sx={{flex: 6}}/>
                    <DialogButton sx={{flex: 2}} isDefault label='Update' onClick={handleUpdate}/>
                    <DialogButton sx={{flex: 2}} onClick={handleClose} label='Cancel' isDefault={false}/>
                </Box>
            </Box>
        </SimpleDialog>
    )
}
UpdateBusLineDialog.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    callback: PropTypes.func,
    busline: PropTypes.object,
    subject: PropTypes.object,
}