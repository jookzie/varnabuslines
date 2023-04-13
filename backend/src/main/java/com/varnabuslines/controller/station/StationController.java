package com.varnabuslines.controller.station;

import com.varnabuslines.business.StationService;
import com.varnabuslines.controller.ListWrapper;
import com.varnabuslines.controller.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/stations")
public class StationController
{
    private final StationService service;

    @GetMapping
    public ResponseEntity<ListWrapper<GetStationResponse>> getAll()
    {
        var array = Mapper.map(service.get(), GetStationResponse[].class);
        return ResponseEntity.ok(new ListWrapper<>(array));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetStationResponse> getById(@PathVariable final long id)
    {
        return ResponseEntity.ok(Mapper.map(service.get(id), GetStationResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<GetStationResponse> create(@RequestBody final CreateStationRequest request)
    {
        var station = service.create(request.getName(), request.getAddress(), request.getCoordinates());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Mapper.map(station, GetStationResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<GetStationResponse> update(@PathVariable final long id,
                                                     @RequestBody final UpdateStationRequest request)
    {
        var opt = service.update(id, request.getName(), request.getAddress(), request.getCoordinates(), request.isAvailable());
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Mapper.map(opt.get(), GetStationResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id)
    {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
