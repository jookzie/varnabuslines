package com.varnabuslines.domain;

import com.varnabuslines.domain.exceptions.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Bus
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Embedded
    @JoinColumn
    @NotNull
    private Coordinates coordinates;

    @NotNull
    private boolean available = true;

    
    public Bus(final Coordinates coordinates)
    {
        setCoordinates(coordinates);
    }
    
    public Bus(final long id, final Coordinates coordinates)
    {
        this(coordinates);
        setId(id);
    }
    
    public void setId(final long id)
    {
        if (id < 0)
            throw new ValidationException(String.format("Id cannot be a negative number. Current value: %s.", id));
        this.id = id;
    }
    
    public void setCoordinates(final Coordinates coordinates)
    {
        if (coordinates == null)
            throw new ValidationException("Coordinates cannot be null.");
        
        this.coordinates = coordinates;
    }
    
    public void setAvailable(final Boolean available)
    {
        if (available == null)
            throw new ValidationException("Available cannot be null.");
        
        this.available = available;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var other = (Bus) o;
        return id == other.id;
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id);
    }
}
