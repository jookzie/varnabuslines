package com.varnabuslines.business;

import com.varnabuslines.domain.Bus;
import com.varnabuslines.domain.Coordinates;
import com.varnabuslines.persistence.BusRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BusService
{
    private BusRepo repository;

    public Bus create(final Coordinates coordinates, final Boolean status)
    {
        var bus = new Bus();
        bus.setCoordinates(coordinates);
        bus.setAvailable(status);
        return repository.save(bus);
    }

    public Iterable<Bus> get()
    {
        return repository.findAll();
    }

    public Optional<Bus> get(final long id)
    {
        return repository.findById(id);
    }

    public Optional<Bus> update(final long id, final Coordinates coordinates, final Boolean status)
    {
        var opt = repository.findById(id);
        if (opt.isEmpty())
            return Optional.empty();

        var bus = opt.get();
        bus.setCoordinates(coordinates);
        bus.setAvailable(status);

        return Optional.of(repository.save(bus));
    }

    public void delete(final long id)
    {
        repository.deleteById(id);
    }
}
