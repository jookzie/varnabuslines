package com.varnabuslines.controller.tickets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketResponse
{
    private long id;
    private float price;
    private long duration;
}
