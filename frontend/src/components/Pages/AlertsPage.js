import {useEffect, useState} from "react";
import {getAllAlerts} from "../../services/alert/AlertService";
import Box from "@mui/material/Box";

export default function AlertsPage() {
    const [alerts, setAlerts] = useState([]);

    useEffect(() => {
        getAllAlerts().then((array) => {
            if(Array.isArray(array)){
                setAlerts(array);
            }
        });
    }, []);

    return (
        <Box sx={{my:10, mx: 5, display: 'flex', flexWrap: 'wrap'}}>
        {alerts.map((a) => (
            <Box key={a.id} sx={{
                m: 2,
                p: 2,
                minWidth: 200,
                minHeight: 200,
                width: 'fit-content',
                height: 'fit-content',
                border: 2,
                borderColor: 'primary.main',
                borderRadius: 5,
            }}>
                <b>Date:</b> {new Date(a.timestamp).toLocaleString()}
                <br/>
                <b>Title:</b> {a.title}
                <br/>
                <b>Description:</b><br/> {a.content}
            </Box>
        ))}
    </Box>);
}