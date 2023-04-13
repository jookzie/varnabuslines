package com.varnabuslines.controller.alert;


import com.varnabuslines.business.AlertService;
import com.varnabuslines.controller.ListWrapper;
import com.varnabuslines.controller.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/alerts")
public class AlertController
{
    private final AlertService service;

    @GetMapping
    public ResponseEntity<ListWrapper<GetAlertResponse>> getAll() {
        var array = Mapper.map(service.get(), GetAlertResponse[].class);
        return ResponseEntity.ok(new ListWrapper<>(array));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetAlertResponse> get(@PathVariable final long id) {
        var opt = service.get(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Mapper.map(opt.get(), GetAlertResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<GetAlertResponse> create(@RequestBody final CreateAlertRequest request) {
        var alert = service.create(request.getTitle(), request.getContent());
        var body = Mapper.map(alert, GetAlertResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<GetAlertResponse> update(@PathVariable final long id,
                                                   @RequestBody final UpdateAlertRequest request) {
        var opt = service.update(id, request.getTitle(), request.getContent());
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Mapper.map(opt.get(), GetAlertResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}