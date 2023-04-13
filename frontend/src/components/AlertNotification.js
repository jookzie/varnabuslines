import Box from "@mui/material/Box";
import * as React from "react";
import {useEffect} from "react";
import CustomButton from "./CustomButton";
import Typography from "@mui/material/Typography";
import CloseIcon from '@mui/icons-material/Close';

export default function AlertNotification(props) {
    const {alert, setAlert} = props;

    useEffect(() => {

    }, [alert]);

    function onDismiss() {
        setAlert(null);
    }

    return (
        alert ?
            <div style={{position: 'relative'}}>
                <div style={{
                    position: 'absolute',
                    width: '300px',
                    height: '200px',
                    left: '75%',
                    top: '100px',
                    zIndex: 100000,
                }}>
                    <Box sx={{
                        border: 2,
                        borderRadius: 5,
                        bgcolor: 'white',
                        opacity: 0.8,
                        borderColor: 'primary.main',
                        width: '100%',
                        height: '250px',
                        p: 2,
                    }}>
                        <Box sx={{
                            width: '100%',
                            height: 3/4,
                        }}>
                            <Typography sx={{
                                fontSize: 30,
                            }}>
                                New alert!
                            </Typography>
                            <b>Date:</b> {alert ? new Date(alert.timestamp).toLocaleString() : ''}
                            <br/>
                            <b>Title:</b> {alert?.title}
                            <br/>
                            <b>Description:</b><br/> {alert?.content}

                        </Box>

                        <Box sx={{
                            display: 'flex',
                            flexDirection: 'row-reverse',
                            width: '100%',
                            height: 1/4,
                        }}>
                            <CustomButton invertColors={true} onClick={onDismiss}>
                                <CloseIcon/>
                                Dismiss
                            </CustomButton>
                        </Box>
                    </Box>
                </div>
            </div>
        : null
    );
}