import * as React from 'react';
import {LeftSide, RightSide} from "./ToolbarTemplates";
import LogoutButton from "../buttons/LogoutButton";
import CustomButton from "../../CustomButton";
import WarningAmberIcon from '@mui/icons-material/WarningAmber';

const UserToolbar = () => (
    <>
        <LeftSide>
            <CustomButton link='/alerts'>
                <WarningAmberIcon/>
                Alerts
            </CustomButton>
        </LeftSide>
        <RightSide>
            <LogoutButton/>
        </RightSide>
    </>
)
export default UserToolbar;