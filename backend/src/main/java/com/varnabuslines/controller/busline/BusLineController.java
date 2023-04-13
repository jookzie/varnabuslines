package com.varnabuslines.controller.busline;

import com.varnabuslines.business.BusLineService;
import com.varnabuslines.controller.ListWrapper;
import com.varnabuslines.controller.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@AllArgsConstructor
@RestController
@RequestMapping("/buslines")
public class BusLineController
{
    private final BusLineService busLineService;

    @GetMapping
    public ResponseEntity<ListWrapper<GetBusLineResponse>> getAll()
    {
        var array = Mapper.map(busLineService.get(), GetBusLineResponse[].class);
        return ResponseEntity.ok(new ListWrapper<>(array));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetBusLineResponse> get(@PathVariable final String id)
    {
        var busLineOpt = busLineService.get(id);
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(Mapper.map(busLineOpt.get(), GetBusLineResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<GetBusLineResponse> create(@RequestBody final CreateBusLineRequest request)
    {
        var busLine = busLineService.create(request.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Mapper.map(busLine, GetBusLineResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<GetBusLineResponse> update(@PathVariable final String id,
                                                     @RequestBody final UpdateBusLineRequest request)
    {
        var busLineOpt = busLineService.update(id, request.getId(), request.isAvailable());
        if (busLineOpt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(Mapper.map(busLineOpt.get(), GetBusLineResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final String id)
    {
        busLineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
