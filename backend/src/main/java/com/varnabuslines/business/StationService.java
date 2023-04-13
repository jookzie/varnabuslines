package com.varnabuslines.business;

import com.varnabuslines.domain.Coordinates;
import com.varnabuslines.domain.Station;
import com.varnabuslines.persistence.StationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class StationService
{
    private final StationRepo repository;

    public Iterable<Station> get()
    {
        return repository.findAll();
    }

    public Optional<Station> get(final long id)
    {
        return repository.findById(id);
    }

    public Iterable<Station> get(final Iterable<Long> ids)
    {
        return repository.findAllById(ids);
    }

    public Station create(final String name, final String address, final Coordinates coordinates)
    {
        var station = new Station();
        station.setName(name);
        station.setAddress(address);
        station.setCoordinates(coordinates);
        return repository.save(station);
    }

    public Optional<Station> update(final long id,
                                    final String name,
                                    final String address,
                                    final Coordinates coordinates,
                                    final Boolean status)
    {
        var opt = get(id);
        if (opt.isEmpty())
            return Optional.empty();

        var station = opt.get();

        station.setName(name);
        station.setAddress(address);
        station.setCoordinates(coordinates);
        station.setAvailable(status);

        return Optional.of(repository.save(station));
    }

    public void delete(final long id)
    {
        repository.deleteById(id);
    }
}
