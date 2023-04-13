package com.varnabuslines.business;

import com.varnabuslines.domain.*;
import com.varnabuslines.persistence.BusLineRepo;
import com.varnabuslines.persistence.BusRepo;
import com.varnabuslines.persistence.StationRepo;
import com.varnabuslines.persistence.TicketRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.varnabuslines.business.utils.impl.WtfJavaAbstractRequestUseCaseImpl.toHashSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@BusinessLayerTest
class BusLineServiceTest
{
    private static final BusLineRepo busLineRepo = mock(BusLineRepo.class);
    private static final BusRepo busRepo = mock(BusRepo.class);
    private static final StationRepo stationRepo = mock(StationRepo.class);
    private static final TicketRepo ticketRepo = mock(TicketRepo.class);

    private static final BusLineService service = new BusLineService(busLineRepo, busRepo, ticketRepo, stationRepo);

    private static final BusLine mockBusLine = new BusLine("mockLine");

    @BeforeEach
    void setUp()
    {
        when(busLineRepo.findAll()).thenReturn(List.of(mockBusLine));
        when(busLineRepo.findById(mockBusLine.getId())).thenReturn(Optional.of(mockBusLine));
        when(busLineRepo.save(any(BusLine.class))).thenReturn(mockBusLine);
    }

    @Test
    void getAll()
    {
        var actual = (Collection<BusLine>) service.get();
        assertTrue(actual.contains(mockBusLine));
    }

    @Test
    void getById()
    {
        var actual = service.get(mockBusLine.getId());
        assertTrue(actual.isPresent());
        assertEquals(mockBusLine, actual.get());
    }

    @Test
    void create()
    {
        var actual = service.create(mockBusLine.getId());
        assertEquals(mockBusLine, actual);
    }

    @Test
    void update()
    {
        var newId = mockBusLine.getId() + '1';
        var actual = service.update(mockBusLine.getId(), newId, true);

        assertTrue(actual.isPresent());
        assertEquals(mockBusLine, actual.get());
    }

    @Test
    void delete()
    {
        assertDoesNotThrow(() -> service.delete(mockBusLine.getId()));
    }

    @Test
    void delete_doesNotThrow()
    {
        assertDoesNotThrow(() -> service.delete(""));
    }

    @Test
    void update_buses()
    {
        var buses = List.of(
                new Bus(0L, new Coordinates(0, 0)),
                new Bus(1L, new Coordinates(1, 1))
        );

        when(busRepo.findAllById(any())).thenReturn(buses);
        var opt = service.updateBuses(mockBusLine.getId(), buses.stream().map(Bus::getId).toList());

        assertAll(
                () -> assertTrue(opt.isPresent()),
                () -> assertEquals(toHashSet(buses), opt.get().getBuses())
        );

    }

    @Test
    void update_tickets()
    {
        var tickets = List.of(
                new Ticket(0L, 10, 10),
                new Ticket(1L, 11, 11)
        );

        when(ticketRepo.findAllById(any())).thenReturn(tickets);
        var opt = service.updateTickets(mockBusLine.getId(), tickets.stream().map(Ticket::getId).toList());

        assertAll(
                () -> assertTrue(opt.isPresent()),
                () -> assertEquals(toHashSet(tickets), opt.get().getTickets())
        );
    }

    @Test
    void update_stations()
    {
        var stations = List.of(
                new Station("00", "00000", new Coordinates(0, 0)),
                new Station("11", "11111", new Coordinates(1, 1))
        );

        when(stationRepo.findAllById(any())).thenReturn(stations);
        var opt = service.updateRoute(mockBusLine.getId(), stations.stream().map(Station::getId).toList());

        assertAll(
                () -> assertTrue(opt.isPresent()),
                () -> assertEquals(toHashSet(stations), toHashSet(opt.get().getStations()))
        );
    }
}