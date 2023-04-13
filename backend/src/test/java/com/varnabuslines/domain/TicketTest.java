package com.varnabuslines.domain;

import com.varnabuslines.business.BusinessLayerTest;
import com.varnabuslines.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@BusinessLayerTest
class TicketTest
{

    @Test
    void setPrice()
    {
        var ticket = new Ticket();
        ticket.setPrice(1.0f);
        assertEquals(1.0f, ticket.getPrice());
    }

    @Test
    void setPrice_negative()
    {
        var ticket = new Ticket();
        assertThrows(ValidationException.class, () -> ticket.setPrice(-1.0f));
    }

    @Test
    void setDuration()
    {
        var ticket = new Ticket();
        ticket.setDuration(1);
        assertEquals(1, ticket.getDuration());
    }

    @Test
    void setDuration_negative()
    {
        var ticket = new Ticket();
        assertThrows(ValidationException.class, () -> ticket.setDuration(-1));
    }

    @Test
    void testEquals()
    {
        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(1.0f);
        ticket.setDuration(1);

        var ticket2 = new Ticket();
        ticket2.setId(1L);
        ticket2.setPrice(2.0f);
        ticket2.setDuration(2);

        assertEquals(ticket, ticket2);
    }

    @Test
    void testEquals_false()
    {
        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(1.0f);
        ticket.setDuration(1);

        var ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setPrice(1.0f);
        ticket2.setDuration(1);

        assertNotEquals(ticket, ticket2);
    }

    @Test
    void testHashCode()
    {
        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(1.0f);
        ticket.setDuration(1);

        assertEquals(1L, ticket.hashCode());
    }

    @Test
    void testHashCode_2()
    {
        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(1.0f);
        ticket.setDuration(1);

        var ticket2 = new Ticket();
        ticket2.setId(1L);
        ticket2.setPrice(1.0f);
        ticket2.setDuration(1);

        assertEquals(ticket.hashCode(), ticket2.hashCode());
    }

    @Test
    void testHashCode_false()
    {
        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(1.0f);
        ticket.setDuration(1);

        var ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setPrice(1.0f);
        ticket2.setDuration(1);

        assertNotEquals(ticket.hashCode(), ticket2.hashCode());
    }
}