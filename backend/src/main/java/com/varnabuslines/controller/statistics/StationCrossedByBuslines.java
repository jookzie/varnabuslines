package com.varnabuslines.controller.statistics;

import com.varnabuslines.controller.station.GetStationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// Represents the station and the buslines that include it
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationCrossedByBuslines
{
    private GetStationResponse station;
    private String[] busLineIds;
}
