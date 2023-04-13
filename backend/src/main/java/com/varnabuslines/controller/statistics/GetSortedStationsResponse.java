package com.varnabuslines.controller.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// Represents an array of stations and the number of buslines that include them
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSortedStationsResponse
{
    private StationCrossedByBuslines[] stations;
}
