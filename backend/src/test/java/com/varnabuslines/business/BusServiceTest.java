package com.varnabuslines.business;

import com.varnabuslines.domain.Bus;
import com.varnabuslines.domain.Coordinates;
import com.varnabuslines.persistence.BusRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@BusinessLayerTest
class BusServiceTest
{
    private static final BusRepo mockRepo = mock(BusRepo.class);
    private static final BusService service = new BusService(mockRepo);
    private static final Bus mockBus = new Bus(0L, new Coordinates(0, 0));

    @BeforeEach
    void setUp()
    {
        when(mockRepo.findAll()).thenReturn(List.of(mockBus));
        when(mockRepo.findById(mockBus.getId())).thenReturn(Optional.of(mockBus));
        when(mockRepo.findById(mockBus.getId())).thenReturn(Optional.of(mockBus));
        when(mockRepo.save(any(Bus.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void getAll()
    {
        var actual = (Collection<Bus>) service.get();
        assertTrue(actual.contains(mockBus));
    }

    @Test
    void getById()
    {
        var actual = service.get(mockBus.getId());
        assertTrue(actual.isPresent());
        assertEquals(mockBus, actual.get());
    }

    @Test
    void create()
    {
        var expected = mockBus;
        var actual = service.create(expected.getCoordinates(), true);
        assertEquals(expected, actual);
    }

    @Test
    void update()
    {
        var expected = new Bus(mockBus.getId(), mockBus.getCoordinates());
        var actual = service.update(expected.getId(), expected.getCoordinates(), true);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void delete()
    {
        assertDoesNotThrow(() -> service.delete(mockBus.getId()));
    }

    @Test
    void delete_doesNotThrow()
    {
        assertDoesNotThrow(() -> service.delete(-1L));
    }

}