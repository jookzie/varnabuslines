package com.varnabuslines.controller.busline.route;

import com.varnabuslines.business.BusLineService;
import com.varnabuslines.controller.Mapper;
import com.varnabuslines.controller.station.GetStationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/buslines/{busLineId}/route")
public class BLRController
{
    private BusLineService busLineService;

    @GetMapping
    public ResponseEntity<GetStationResponse[]> getRoutes(@PathVariable final String busLineId)
    {
        var busLineOpt = busLineService.get(busLineId);
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var body = Mapper.map(busLineOpt.get().getStations(), GetStationResponse[].class);
        return ResponseEntity.ok(body);
    }

    @PutMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<GetStationResponse[]> updateBuses(@PathVariable final String busLineId,
                                                            @RequestBody final UpdateRouteRequest request)
    {
        var busLineOpt = busLineService.updateRoute(busLineId, List.of(request.getStationIds()));
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var responseBody = Mapper.map(busLineOpt.get().getStations(), GetStationResponse[].class);

        return ResponseEntity.ok(responseBody);
    }
}
