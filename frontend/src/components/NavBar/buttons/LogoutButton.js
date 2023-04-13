import {logout} from "../../../services/authentication/Authentication";
import CustomButton from "../../CustomButton";
import LogoutIcon from "@mui/icons-material/Logout";
import * as React from "react";
import {Link} from "react-router-dom";

const LogoutButton = () => {
    return(
        <CustomButton
            onClick={logout}
            component={Link}
            to='/'
        >
            <LogoutIcon/>
            Logout
        </CustomButton>
    )
}
export default LogoutButton;