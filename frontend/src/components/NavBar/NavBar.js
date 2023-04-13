import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import AirportShuttleIcon from '@mui/icons-material/AirportShuttle';
import Configuration from "../../local/Configuration";
import {Link} from "react-router-dom";


const NavBar = ({toolbar}) => {
    return (
        <AppBar position="fixed" sx={{zIndex: 10001}}>
            <Toolbar disableGutters sx={{mx: '5%'}}>
                <AirportShuttleIcon sx={{display: 'flex', mx: 1}}/>
                <Typography
                    variant="h6"
                    component={Link}
                    to="/"
                    sx={{
                        mr: 2,
                        display: 'flex',
                        whiteSpace: 'nowrap',
                        fontFamily: 'monospace',
                        fontWeight: 700,
                        letterSpacing: '.3rem',
                        color: 'inherit',
                        textDecoration: 'none',
                    }}
                >
                    {Configuration.appName.toUpperCase()}
                </Typography>
                {toolbar}
            </Toolbar>
        </AppBar>
    );
};
export default NavBar;