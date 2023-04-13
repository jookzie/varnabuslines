package com.varnabuslines.controller.tickets;

import com.varnabuslines.business.TicketService;
import com.varnabuslines.controller.ListWrapper;
import com.varnabuslines.controller.Mapper;
import com.varnabuslines.domain.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/tickets")
public class TicketController
{
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<ListWrapper<GetTicketResponse>> getAll()
    {
        var array = Mapper.map(ticketService.get(), GetTicketResponse[].class);
        return ResponseEntity.ok(new ListWrapper<>(array));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetTicketResponse> get(@PathVariable final long id)
    {
        Optional<Ticket> opt = ticketService.get(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(Mapper.map(opt.get(), GetTicketResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<GetTicketResponse> create(@RequestBody final CreateTicketRequest request)
    {
        Ticket ticket = ticketService.create(request.getPrice(), request.getDuration());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Mapper.map(ticket, GetTicketResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<GetTicketResponse> update(@PathVariable final long id,
                                                    @RequestBody final UpdateTicketRequest request)
    {
        var ticketOpt = ticketService.update(id, request.getPrice(), request.getDuration());
        if (ticketOpt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(Mapper.map(ticketOpt.get(), GetTicketResponse.class));
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id)
    {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
