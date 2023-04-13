package com.varnabuslines.business;

import com.varnabuslines.domain.Ticket;
import com.varnabuslines.persistence.TicketRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TicketService
{
    private TicketRepo repository;

    public Ticket create(final float price, final int duration)
    {
        return repository.save(new Ticket(price, duration));
    }

    public Optional<Ticket> get(final long id)
    {
        return repository.findById(id);
    }

    public Iterable<Ticket> get()
    {
        return repository.findAll();
    }

    public Optional<Ticket> update(final long id, final float price, final int duration)
    {
        var opt = repository.findById(id);
        if (opt.isEmpty())
            return Optional.empty();
        var ticket = opt.get();
        ticket.setPrice(price);
        ticket.setDuration(duration);
        return Optional.of(repository.save(ticket));
    }

    public void delete(final long id)
    {
        repository.deleteById(id);
    }
}
