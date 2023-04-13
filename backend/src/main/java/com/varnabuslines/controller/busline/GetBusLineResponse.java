package com.varnabuslines.controller.busline;

import com.varnabuslines.controller.bus.GetBusResponse;
import com.varnabuslines.controller.station.GetStationResponse;
import com.varnabuslines.controller.tickets.GetTicketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBusLineResponse
{
    private String id;
    private boolean available;
    private GetBusResponse[] buses;
    private GetStationResponse[] stations;
    private GetTicketResponse[] tickets;
}
