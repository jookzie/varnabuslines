import * as React from 'react';
import Card from '@mui/material/Card';
import Box from "@mui/material/Box";
import CustomButton from "../../CustomButton";
import {alpha} from "@mui/material";
import SearchPopupBar from "../../SearchPopupBar";
import {getAllStations} from "../../../services/station/StationService";
import {useEffect, useState} from "react";

export default function BuslinesCard(props) {
    const {
        buslines,
        onBuslineClick,
        selectedStation,
        setSelectedStation
    } = props;


    const [stations, setAllStations] = useState([]);
    function handleBuslineClick(busline) {
        onBuslineClick(busline);
    }

    useEffect(() => {
        getAllStations().then((response) => {
            setAllStations(response || []);
        });
    }, []);



    return (
        <Card sx={{
            width: 350,
            height: 500,
            marginLeft: 7,
            marginTop: 14,
            borderRadius: 8,
            bgcolor: alpha('#ffffff', 0.8),
            boxShadow: 10,
            border: 2,
            borderColor: 'primary.main',
        }}>
            <SearchPopupBar
                value={selectedStation}
                setValue={setSelectedStation}
                items={stations}
                displayProperty='name'
                placeholder='Filter by stations...'
                sx={{
                    m: 2,
                    borderRadius: 8
                }}
            />
            <Box sx={{
                margin: 2,
                width: 335,
                height: 380,
                display: 'flex',
                alignContent: 'flex-start',
                flexWrap: 'wrap',
                overflow: 'auto',
                gap: 2.5,
            }}>
                {Array.isArray(buslines) && buslines.map(busline => (
                    <CustomButton
                        onClick={() => handleBuslineClick(busline)}
                        key={busline.id}
                        invertColors={true}
                        toggling={true}
                        sx={{
                            borderColor: 'primary.main',
                            borderRadius: 2,
                            fontSize: 16,
                            width: 'fit-content',
                            height: 'fit-content',
                        }}>
                        {busline.id}
                    </CustomButton>
                ))}
            </Box>
        </Card>
    );
}