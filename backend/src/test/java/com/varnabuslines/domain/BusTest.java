package com.varnabuslines.domain;

import com.varnabuslines.business.BusinessLayerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@BusinessLayerTest
class BusTest
{
    @Test
    void testEquals_true()
    {
        var bus = new Bus();
        bus.setId(1L);

        var bus2 = new Bus();
        bus2.setId(1L);

        assertEquals(bus, bus2);
    }

    @Test
    void testEquals_false()
    {
        var bus = new Bus();
        bus.setId(1L);

        var bus2 = new Bus();
        bus2.setId(2L);

        assertNotEquals(bus, bus2);
    }

    @Test
    void testHashCode_true()
    {
        var bus = new Bus();
        bus.setId(1L);

        assertEquals(1L, bus.hashCode());
    }

    @Test
    void testHashCode_true_2()
    {
        var bus = new Bus();
        bus.setId(1L);

        var bus2 = new Bus();
        bus2.setId(1L);

        assertEquals(bus.hashCode(), bus2.hashCode());
    }

    @Test
    void testHashCode_false()
    {
        var bus = new Bus();
        bus.setId(1L);

        var bus2 = new Bus();
        bus2.setId(2L);

        assertNotEquals(bus.hashCode(), bus2.hashCode());
    }
}