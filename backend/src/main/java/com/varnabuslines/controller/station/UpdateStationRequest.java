package com.varnabuslines.controller.station;

import com.varnabuslines.domain.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStationRequest
{
    private String name;
    private String address;
    private Coordinates coordinates;
    private boolean available;
}
