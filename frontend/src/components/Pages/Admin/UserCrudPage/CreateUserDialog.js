import * as React from 'react';
import PropTypes from 'prop-types';
import SimpleDialog from "../../../Dialog/SimpleDialog";
import TextField from "@mui/material/TextField";
import DialogButton from "../../../Dialog/DialogButton";
import ComboBox from "../../../ComboBox";
import {Roles} from "../../../../services/authorization/AuthorizationService";
import Box from "@mui/material/Box";
import {createUser} from "../../../../services/user/UserService";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import Typography from "@mui/material/Typography";
import {createAdmin} from "../../../../services/admin/AdminService";

export default function CreateUserDialog(props) {
    const {open, setOpen, callback} = props;
    const [errorMessage, setErrorMessage] = React.useState('');
    const [role, setRole] = React.useState(null);

    function handleClose(){
        setOpen(false);
        setErrorMessage('');
    }

    function handleCreate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        const email = data.get('email');
        const password = data.get('password');
        const confirmPassword = data.get('confirm-password');

        if (password !== confirmPassword) {
            setErrorMessage('Passwords do not match.');
            return;
        }
        let promise = null;
        if(role === Roles.ADMIN){
            promise = createAdmin(email, password);
        }
        else if(role === Roles.USER){
            promise = createUser(email, password);
        }
        else{
            setErrorMessage('No role chosen.');
            return;
        }
        promise.then((response) => {
            if(response === ResponseCode.SUCCESS){
                callback?.();
                handleClose();
            }
            else{
                setErrorMessage(getMessageFromErrorCode(response) || 'Unknown error.');
            }
        });
    }

    return (
        <SimpleDialog title='Create a user' open={open}>
            <Box component="form" noValidate onSubmit={handleCreate}
                 sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: 3,
                    width: 300,
            }}>

                <TextField required id="email" label="Email" name='email' variant="outlined" />
                <TextField required id="password" label="Password" name='password' type='password' variant="outlined" />
                <TextField required id="confirm-password" label="Confirm Password" name='confirm-password' type='password' variant="outlined" />
                <ComboBox options={Object.keys(Roles)} label='Role' placeholder='Role' value={role} setValue={setRole}/>
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
    );
}


CreateUserDialog.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    callback: PropTypes.func
};
