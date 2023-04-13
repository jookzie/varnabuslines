package com.varnabuslines.business;

import com.varnabuslines.domain.Ticket;
import com.varnabuslines.persistence.TicketRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@BusinessLayerTest
class TicketServiceTest
{
    private static final TicketRepo mockRepo = mock(TicketRepo.class);
    private static final TicketService service = new TicketService(mockRepo);
    private static final List<Ticket> mocks = List.of(
            new Ticket(0L, 1, 60),
            new Ticket(1L, 2, 120)
    );

    @BeforeEach
    void setUp()
    {
        when(mockRepo.findAll()).thenReturn(new HashSet<>(mocks));
        when(mockRepo.findById(mocks.get(0).getId())).thenReturn(Optional.ofNullable(mocks.get(0)));
        when(mockRepo.findById(mocks.get(1).getId())).thenReturn(Optional.ofNullable(mocks.get(1)));
        when(mockRepo.save(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void getAll()
    {
        var expected = new HashSet<>(mocks);
        var actual = (Collection<Ticket>) service.get();
        assertTrue(actual.containsAll(expected));
    }

    @Test
    void getById()
    {
        var expected = mocks.get(0);
        var actual = service.get(mocks.get(0).getId());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void create()
    {
        var expected = mocks.get(0);
        var actual = service.create(expected.getPrice(), expected.getDuration());
        assertEquals(expected, actual);
    }

    @Test
    void update()
    {
        var expected = new Ticket(mocks.get(0).getId(), 3, 180);
        var actual = service.update(expected.getId(), expected.getPrice(), expected.getDuration());

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void delete()
    {
        assertDoesNotThrow(() -> service.delete(mocks.get(0).getId()));
    }

    @Test
    void delete_doesNotThrow()
    {
        assertDoesNotThrow(() -> service.delete(-1L));
    }

}