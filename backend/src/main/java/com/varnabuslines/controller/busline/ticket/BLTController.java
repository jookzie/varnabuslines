package com.varnabuslines.controller.busline.ticket;

import com.varnabuslines.business.BusLineService;
import com.varnabuslines.controller.Mapper;
import com.varnabuslines.controller.tickets.GetTicketResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/buslines/{busLineId}/tickets")
public class BLTController
{
    private BusLineService busLineService;

    @GetMapping
    public ResponseEntity<GetTicketResponse[]> getTickets(@PathVariable final String busLineId)
    {
        var busLineOpt = busLineService.get(busLineId);
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var body = Mapper.map(busLineOpt.get().getTickets(), GetTicketResponse[].class);
        return ResponseEntity.ok(body);
    }

    @PutMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<GetTicketResponse[]> updateTickets(@PathVariable final String busLineId,
                                                            @RequestBody final UpdateTicketsRequest request)
    {
        var busLineOpt = busLineService.updateTickets(busLineId, List.of(request.getTicketIds()));
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();

        var body = Mapper.map(busLineOpt.get().getTickets(), GetTicketResponse[].class);
        return ResponseEntity.ok(body);
    }

}
