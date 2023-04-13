package com.varnabuslines.controller.bus;

import com.varnabuslines.business.BusService;
import com.varnabuslines.controller.ListWrapper;
import com.varnabuslines.controller.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/buses")
public class BusController
{
    private final BusService service;

    @GetMapping
    public ResponseEntity<ListWrapper<GetBusResponse>> getAll()
    {
        var array = Mapper.map(service.get(), GetBusResponse[].class);
        return ResponseEntity.ok(new ListWrapper<>(array));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetBusResponse> get(@PathVariable final long id)
    {
        var opt = service.get(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Mapper.map(opt.get(), GetBusResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<GetBusResponse> create(@RequestBody final CreateBusRequest request)
    {
        var bus = service.create(request.getCoordinates(), request.isAvailable());
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.map(bus, GetBusResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<GetBusResponse> update(@PathVariable final long id,
                                                 @RequestBody final UpdateBusRequest request)
    {
        var opt = service.update(id, request.getCoordinates(), request.isAvailable());
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Mapper.map(opt.get(), GetBusResponse.class));

    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id)
    {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
