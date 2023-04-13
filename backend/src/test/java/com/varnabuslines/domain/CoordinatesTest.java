package com.varnabuslines.domain;

import com.varnabuslines.business.BusinessLayerTest;
import com.varnabuslines.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@BusinessLayerTest
class CoordinatesTest
{
    @Test
    void testLatitude_outOfBounds()
    {
        assertThrows(ValidationException.class, () -> new Coordinates(91, 0));
        assertThrows(ValidationException.class, () -> new Coordinates(-91, 0));
    }

    @Test
    void testLatitude_inBounds()
    {
        assertDoesNotThrow(() -> new Coordinates(0, 0));
        assertDoesNotThrow(() -> new Coordinates(90, 0));
        assertDoesNotThrow(() -> new Coordinates(-90, 0));
    }

    @Test
    void testLongitude_outOfBounds()
    {
        assertThrows(ValidationException.class, () -> new Coordinates(0, 181));
        assertThrows(ValidationException.class, () -> new Coordinates(0, -181));
    }

    @Test
    void testLongitude_inBounds()
    {
        assertDoesNotThrow(() -> new Coordinates(0, 0));
        assertDoesNotThrow(() -> new Coordinates(0, 180));
        assertDoesNotThrow(() -> new Coordinates(0, -180));
    }
}