package com.varnabuslines.controller.busline.bus;

import com.varnabuslines.business.BusLineService;
import com.varnabuslines.controller.Mapper;
import com.varnabuslines.controller.bus.GetBusResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/buslines/{busLineId}/buses")
public class BLBController
{
    private BusLineService busLineService;

    @GetMapping
    public ResponseEntity<GetBusResponse[]> getBuses(@PathVariable final String busLineId)
    {
        var busLineOpt = busLineService.get(busLineId);
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var body = Mapper.map(busLineOpt.get().getBuses(), GetBusResponse[].class);
        return ResponseEntity.ok(body);
    }

    @PutMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<GetBusResponse[]> updateBuses(@PathVariable final String busLineId,
                                                          @RequestBody final UpdateBusesRequest request)
    {
        var busLineOpt = busLineService.updateBuses(busLineId, List.of(request.getBusIds()));
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var responseBody = Mapper.map(busLineOpt.get().getBuses(), GetBusResponse[].class);

        return ResponseEntity.ok(responseBody);
    }
}
