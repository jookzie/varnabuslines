import SimpleDialog from "../../../Dialog/SimpleDialog";
import PropTypes from "prop-types";
import * as React from "react";
import {Roles} from "../../../../services/authorization/AuthorizationService";
import {getMessageFromErrorCode, ResponseCode} from "../../../../services/utils/ErrorHandler";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import ComboBox from "../../../ComboBox";
import Typography from "@mui/material/Typography";
import DialogButton from "../../../Dialog/DialogButton";
import {updateAsAdmin} from "../../../../services/admin/AdminService";

export default function UpdateUserDialog(props) {
    const {user, open, setOpen, callback} = props;
    const [errorMessage, setErrorMessage] = React.useState('');

    const rolesEnum = Object.keys(Roles);

    const [role, setRole] = React.useState(user?.role);

    function handleClose(){
        setOpen(false);
        setErrorMessage('');
    }

    function handleUpdate(event) {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        const email = data.get('email');
        const password = data.get('password');
        const confirmPassword = data.get('confirm-password');

        if (password !== confirmPassword) {
            setErrorMessage('Passwords do not match.');
            return;
        }

        updateAsAdmin(user.id, email, password, role).then((response) => {
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
        <SimpleDialog title='Update a user' open={open}>
            <Box component="form" noValidate onSubmit={handleUpdate}
                 sx={{
                     display: 'flex',
                     flexDirection: 'column',
                     gap: 3,
                     width: 300,
                 }}>

                <TextField required id="email" label="Email" name='email' variant="outlined" defaultValue={user?.email} />
                <TextField required id="password" label="New Password" name='password' type='password' variant="outlined"/>
                <TextField required id="confirm-password" label="Confirm New Password" name='confirm-password' type='password' variant="outlined" />
                <ComboBox options={rolesEnum} label='Role' placeholder='Role' value={role} setValue={setRole}/>
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
    );
}


UpdateUserDialog.propTypes = {
    user: PropTypes.object,
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    callback: PropTypes.func
};
