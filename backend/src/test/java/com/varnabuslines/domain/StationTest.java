package com.varnabuslines.domain;

import com.varnabuslines.business.BusinessLayerTest;
import com.varnabuslines.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@BusinessLayerTest
class StationTest
{


    @Test
    void setName()
    {
        var station = new Station();
        var name = "a".repeat(Constraints.MIN_BUSSTOP_NAME_LENGTH);
        station.setName(name);

        assertEquals(name, station.getName());
    }

    @Test
    void setName_testBounds()
    {
        var station = new Station();
        var minName = "a".repeat(Constraints.MIN_BUSSTOP_NAME_LENGTH);
        var maxName = "a".repeat(Constraints.MAX_BUSSTOP_NAME_LENGTH);

        var minNameLess = "a".repeat(Constraints.MIN_BUSSTOP_NAME_LENGTH - 1);
        var maxNamePlus = "a".repeat(Constraints.MAX_BUSSTOP_NAME_LENGTH + 1);

        assertDoesNotThrow(() -> station.setName(minName));

        assertDoesNotThrow(() -> station.setName(maxName));

        assertThrows(ValidationException.class,
                () -> station.setName(minNameLess));

        assertThrows(ValidationException.class,
                () -> station.setName(maxNamePlus));
    }

    @Test
    void setAddress()
    {
        var station = new Station();
        var address = "a".repeat(Constraints.MIN_BUSSTOP_ADDRESS_LENGTH);
        station.setAddress(address);

        assertEquals(address, station.getAddress());
    }

    @Test
    void setAddress_testBounds()
    {
        var station = new Station();
        var minAddress = "a".repeat(Constraints.MIN_BUSSTOP_ADDRESS_LENGTH);
        var maxAddress = "a".repeat(Constraints.MAX_BUSSTOP_ADDRESS_LENGTH);

        var minAddressLess = "a".repeat(Constraints.MIN_BUSSTOP_ADDRESS_LENGTH - 1);
        var maxAddressPlus = "a".repeat(Constraints.MAX_BUSSTOP_ADDRESS_LENGTH + 1);

        assertDoesNotThrow(() -> station.setAddress(minAddress));

        assertDoesNotThrow(() -> station.setAddress(maxAddress));

        assertThrows(ValidationException.class,
                () -> station.setAddress(minAddressLess));

        assertThrows(ValidationException.class,
                () -> station.setAddress(maxAddressPlus));
    }

    @Test
    void setId()
    {
        var station = new Station();

        station.setId(1L);
        assertEquals(1L, station.getId());

        station.setId(2L);
        assertEquals(2L, station.getId());
    }

    @Test
    void setAvailable()
    {
        var station = new Station();
        station.setAvailable(true);
        assertTrue(station.isAvailable());

        station.setAvailable(false);
        assertFalse(station.isAvailable());
    }

    @Test
    void setCoordinates()
    {
        var station = new Station();
        var coordinates = new Coordinates(1, 1);
        station.setCoordinates(coordinates);
        assertEquals(coordinates, station.getCoordinates());
    }

    @Test
    void testEquals()
    {
        var station = new Station();
        station.setId(1L);

        var station2 = new Station();
        station2.setId(1L);

        assertEquals(station, station2);
    }

    @Test
    void testEquals_false()
    {
        var station = new Station();
        station.setId(1L);

        var station2 = new Station();
        station2.setId(2L);

        assertNotEquals(station, station2);
    }

    @Test
    void testHashCode()
    {
        var station = new Station();
        station.setId(1L);

        var station2 = new Station();
        station2.setId(1L);

        assertEquals(station.hashCode(), station2.hashCode());
    }

    @Test
    void testHashCode_false()
    {
        var station = new Station();
        station.setId(1L);

        var station2 = new Station();
        station2.setId(2L);

        assertNotEquals(station.hashCode(), station2.hashCode());
    }
}