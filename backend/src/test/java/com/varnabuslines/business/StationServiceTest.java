package com.varnabuslines.business;

import com.varnabuslines.domain.Coordinates;
import com.varnabuslines.domain.Station;
import com.varnabuslines.persistence.StationRepo;
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
class StationServiceTest
{
    private static final StationRepo mockRepo = mock(StationRepo.class);
    private static final StationService service = new StationService(mockRepo);
    private static final Station mockStation = new Station("name0", "address0", new Coordinates(0, 0));

    @BeforeEach
    void setUp()
    {
        when(mockRepo.findAll()).thenReturn(new HashSet<>(List.of(mockStation)));
        when(mockRepo.findById(mockStation.getId())).thenReturn(Optional.of(mockStation));
        when(mockRepo.findById(mockStation.getId())).thenReturn(Optional.of(mockStation));
        when(mockRepo.save(any(Station.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void getAll()
    {
        var expected = new HashSet<>(List.of(mockStation));
        var actual = (Collection<Station>) service.get();
        assertTrue(actual.containsAll(expected));
    }

    @Test
    void getById()
    {
        var actual = service.get(mockStation.getId());
        assertTrue(actual.isPresent());
        assertEquals(mockStation, actual.get());
    }

    @Test
    void create()
    {
        var expected = mockStation;
        var actual = service.create(expected.getName(), expected.getAddress(), expected.getCoordinates());
        assertEquals(expected, actual);
    }

    @Test
    void update()
    {
        var expected = mockStation;
        var actual = service.update(expected.getId(), expected.getName(), expected.getAddress(), expected.getCoordinates(), true);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void delete()
    {
        assertDoesNotThrow(() -> service.delete(mockStation.getId()));
    }

    @Test
    void delete_doesNotThrow()
    {
        assertDoesNotThrow(() -> service.delete(-1L));
    }

}