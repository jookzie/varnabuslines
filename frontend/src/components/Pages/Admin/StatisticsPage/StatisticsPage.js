import {useEffect, useState} from "react";
import {Box} from "@mui/system";
import {getStationStatistics} from "../../../../services/statistics/StatisticsService";
import EnhancedTable from "../../../EnhancedTable/EnhancedTable";
import * as React from "react";
export default function StatisticsPage() {
    const [rows, setRows] = useState([]);

    useEffect(() => {
        if(rows.length === 0){
            getStationStatistics().then((stationBuslinePairs) => {
                const rows = stationBuslinePairs.map((pair) => {
                    return {
                        stationName: pair.station.name,
                        buslinesCount: pair.busLineIds.length,
                        busLineIds: pair.busLineIds
                    }
                });
                setRows(rows);
            });
        }
    }, [rows]);


    return (
        <Box sx={{
            transform: 'translate(0, -10%)',
        }}>
            <EnhancedTable
                title='Stations ranked by the count of buslines crossing them'
                rows={rows}
                compactViewEnabled
            />
        </Box>

    )
}
