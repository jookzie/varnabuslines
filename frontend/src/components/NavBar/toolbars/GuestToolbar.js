import * as React from 'react';
import CustomButton from "../../CustomButton";
import LoginIcon from '@mui/icons-material/Login';
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import {LeftSide, RightSide} from "./ToolbarTemplates";
import WarningAmberIcon from "@mui/icons-material/WarningAmber";

const GuestToolbar = () => (
    <>
        <LeftSide>
            <CustomButton link='/alerts'>
                <WarningAmberIcon/>
                Alerts
            </CustomButton>
        </LeftSide>
        <RightSide>
            <CustomButton link='/authentication'>
                <LoginIcon/>
                Log in
            </CustomButton>
            <CustomButton link='/register'>
                <AppRegistrationIcon/>
                Register
            </CustomButton>
        </RightSide>
    </>

);

export default GuestToolbar;