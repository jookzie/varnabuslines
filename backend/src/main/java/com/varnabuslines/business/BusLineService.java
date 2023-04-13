package com.varnabuslines.business;

import com.varnabuslines.domain.BusLine;
import com.varnabuslines.domain.Station;
import com.varnabuslines.persistence.BusLineRepo;
import com.varnabuslines.persistence.BusRepo;
import com.varnabuslines.persistence.StationRepo;
import com.varnabuslines.persistence.TicketRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static com.varnabuslines.business.utils.impl.WtfJavaAbstractRequestUseCaseImpl.toHashSet;
import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;


@AllArgsConstructor
@Service
public class BusLineService
{
    private BusLineRepo busLineRepo;
    private BusRepo busRepo;
    private TicketRepo ticketRepo;
    private StationRepo stationRepo;

    public BusLine create(final String id)
    {
        return busLineRepo.save(new BusLine(id));
    }

    public Iterable<BusLine> get()
    {
        return busLineRepo.findAll();
    }

    public Optional<BusLine> get(final String id)
    {
        if(isNullOrEmpty(id))
            return Optional.empty();

        return busLineRepo.findById(id);
    }

    public Iterable<BusLine> get(final Iterable<String> ids)
    {
        return busLineRepo.findAllById(ids);
    }

    public Optional<BusLine> update(final String id, final String newId, final Boolean status)
    {
        var opt = get(id);
        if (opt.isEmpty())
            return Optional.empty();

        var busLine = opt.get();
        busLine.setId(newId);
        busLine.setAvailable(status);

        return Optional.of(busLineRepo.save(busLine));
    }

    public Optional<BusLine> updateBuses(final String id, final Iterable<Long> busIds)
    {
        var opt = get(id);
        if (opt.isEmpty())
            return Optional.empty();

        var busLine = opt.get();
        var buses = toHashSet(busRepo.findAllById(busIds));
        busLine.setBuses(buses);

        return Optional.of(busLineRepo.save(busLine));
    }

    public Optional<BusLine> updateTickets(final String id, final Iterable<Long> ticketIds)
    {
        var opt = get(id);
        if (opt.isEmpty())
            return Optional.empty();

        var busLine = opt.get();
        var tickets = toHashSet(ticketRepo.findAllById(ticketIds));
        busLine.setTickets(tickets);

        return Optional.of(busLineRepo.save(busLine));
    }

    public Optional<BusLine> updateRoute(final String busLineId, final Iterable<Long> stationIds)
    {
        var busLineOpt = get(busLineId);
        if (busLineOpt.isEmpty())
            return Optional.empty();

        var busLine = busLineOpt.get();

        var hashMap = new HashMap<Long, Station>();

        for (var station : stationRepo.findAllById(stationIds))
        {
            hashMap.put(station.getId(), station);
        }

        var list = new ArrayList<Station>();
        for (var stationId : stationIds)
        {
            if(hashMap.containsKey(stationId))
                list.add(hashMap.get(stationId));
        }

        busLine.setStations(list);

        return Optional.of(busLineRepo.save(busLine));
    }

    public void delete(final String id)
    {
        busLineRepo.deleteById(id);
    }
}
