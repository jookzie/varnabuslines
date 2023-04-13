package com.varnabuslines.domain;

import com.varnabuslines.domain.exceptions.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Coordinates
{
    public Coordinates(float latitude, float longitude)
    {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    private void setLatitude(final float latitude)
    {
        if (latitude < -90 || latitude > 90)
            throw new ValidationException("Latitude must be between -90 and 90 degrees.");
        this.latitude = latitude;
    }

    private void setLongitude(final float longitude)
    {
        if (longitude < -180 || longitude > 180)
            throw new ValidationException("Longitude must be between -180 and 180 degrees.");
        this.longitude = longitude;
    }

    private float latitude;
    private float longitude;
}
