package com.varnabuslines.controller.bus;

import com.varnabuslines.domain.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBusResponse
{
    private long id;
    private Coordinates coordinates;
    private boolean available;
}
