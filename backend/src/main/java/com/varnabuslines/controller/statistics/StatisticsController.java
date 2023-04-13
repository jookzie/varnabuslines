package com.varnabuslines.controller.statistics;

import com.varnabuslines.business.BusLineService;
import com.varnabuslines.business.StationService;
import com.varnabuslines.controller.Mapper;
import com.varnabuslines.controller.station.GetStationResponse;
import com.varnabuslines.domain.BusLine;
import com.varnabuslines.domain.Station;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

import java.util.*;

import static com.varnabuslines.business.utils.impl.WtfJavaAbstractRequestUseCaseImpl.getCount;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
@RolesAllowed("ROLE_ADMIN")
public class StatisticsController
{
    private final BusLineService busLineService;
    private final StationService stationService;

    @GetMapping("/stations")
    public ResponseEntity<GetSortedStationsResponse> getSortedStationsResponse()
    {
        Iterable<BusLine> buslines = busLineService.get();
        Iterable<Station> stations = stationService.get();

        // Initialize hashmap with stations as keys and empty lists as values
        var stationsCrossingBuslinesMap = new HashMap<Station, List<BusLine>>();
        for(var station : stations)
        {
            stationsCrossingBuslinesMap.put(station, new ArrayList<>());
        }

        // Add buslines to the lists of stations
        for(var busline : buslines)
        {
            for(var station : busline.getStations())
            {
                stationsCrossingBuslinesMap.get(station).add(busline);
            }
        }

        // Extract busline identifiers and convert the map to a list
        var stationsCrossingBuslinesList = new ArrayList<StationCrossedByBuslines>();
        for(var station : stationsCrossingBuslinesMap.keySet())
        {
            var buslinesIds = stationsCrossingBuslinesMap.get(station)
                    .stream()
                    .map(BusLine::getId)
                    .toArray(String[]::new);
            stationsCrossingBuslinesList.add(new StationCrossedByBuslines(
                    Mapper.map(station, GetStationResponse.class),
                    buslinesIds
            ));
        }

        // Sort them by the count of buslines crossing a station
        stationsCrossingBuslinesList.sort(Comparator.comparingInt(o ->
                ((StationCrossedByBuslines)o).getBusLineIds().length).reversed());

        return ResponseEntity.ok(new GetSortedStationsResponse(
                stationsCrossingBuslinesList.toArray(StationCrossedByBuslines[]::new)));
    }
}
