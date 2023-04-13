import * as React from 'react';
import CustomButton from "../../CustomButton";
import {LeftSide, RightSide} from "./ToolbarTemplates";
import LogoutButton from "../buttons/LogoutButton";
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';
import LinearScaleIcon from '@mui/icons-material/LinearScale';
import DirectionsBusIcon from '@mui/icons-material/DirectionsBus';
import PlaceIcon from '@mui/icons-material/Place';
import ConfirmationNumberIcon from '@mui/icons-material/ConfirmationNumber';
import WarningAmberIcon from "@mui/icons-material/WarningAmber";
import AssessmentOutlinedIcon from '@mui/icons-material/AssessmentOutlined';
const AdminToolbar = () => {
    return(
        <>
            <LeftSide>
                <CustomButton link='/admin/users'>
                    <PeopleAltIcon/>
                    Users
                </CustomButton>
                <CustomButton link='/admin/buslines'>
                    <LinearScaleIcon/>
                    Bus Lines
                </CustomButton>
                <CustomButton link='/admin/buses'>
                    <DirectionsBusIcon/>
                    Buses
                </CustomButton>
                <CustomButton link='/admin/stations'>
                    <PlaceIcon/>
                    Stations
                </CustomButton>
                <CustomButton link='/admin/tickets'>
                    <ConfirmationNumberIcon/>
                    Tickets
                </CustomButton>
                <CustomButton link='/admin/alerts'>
                    <WarningAmberIcon/>
                    Alerts
                </CustomButton>
                <CustomButton link='/admin/statistics'>
                    <AssessmentOutlinedIcon/>
                    Statistics
                </CustomButton>
            </LeftSide>
            <RightSide>
                <LogoutButton/>
            </RightSide>
        </>
    )
}

export default AdminToolbar;