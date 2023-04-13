package com.varnabuslines.domain;

import com.varnabuslines.business.BusinessLayerTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@BusinessLayerTest
class BusLineTest
{
    @Test
    void lineAvailable_noBuses()
    {
        var busLine = new BusLine();
        busLine.setAvailable(true);

        assertFalse(busLine.lineAvailable());
    }

    @Test
    void lineAvailable_activeLine_inactiveBuses()
    {
        var busLine = new BusLine();
        busLine.setAvailable(true);
        busLine.setBuses(Set.of(new Bus(){{setAvailable(false);}}));

        assertFalse(busLine.lineAvailable());
    }

    @Test
    void lineAvailable_inactiveLine_activeBuses()
    {
        var busLine = new BusLine();
        busLine.setAvailable(false);
        busLine.setBuses(Set.of(new Bus(){{setAvailable(true);}}));

        assertFalse(busLine.lineAvailable());
    }

    @Test
    void lineAvailable_activeLine_activeBuses()
    {
        var busLine = new BusLine();
        busLine.setAvailable(true);
        busLine.setBuses(Set.of(new Bus(){{setAvailable(true);}}));

        assertTrue(busLine.lineAvailable());
    }

    @Test
    void testEquals()
    {
        var busLine1 = new BusLine();
        busLine1.setId("1");

        var busLine2 = new BusLine();
        busLine2.setId("1");

        assertEquals(busLine1, busLine2);
    }

    @Test
    void testEquals_fail()
    {
        var busLine1 = new BusLine();
        busLine1.setId("1");

        var busLine2 = new BusLine();
        busLine2.setId("2");

        assertNotEquals(busLine1, busLine2);
    }

    @Test
    void testHashCode()
    {
        var busLine1 = new BusLine();
        busLine1.setId("1");

        var busLine2 = new BusLine();
        busLine2.setId("1");

        assertEquals(busLine1.hashCode(), busLine2.hashCode());
    }

    @Test
    void testHashCode_fail()
    {
        var busLine1 = new BusLine();
        busLine1.setId("1");

        var busLine2 = new BusLine();
        busLine2.setId("2");

        assertNotEquals(busLine1.hashCode(), busLine2.hashCode());
    }
}