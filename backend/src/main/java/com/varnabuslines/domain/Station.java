package com.varnabuslines.domain;

import com.varnabuslines.domain.exceptions.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

@Entity
@NoArgsConstructor
@Getter
public class Station
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private long id;

    @Setter
    private boolean available = true;

    @Embedded
    @JoinColumn
    @Setter
    private Coordinates coordinates;

    @Column(unique = true)
    @NotNull
    private String name;

    @NotNull
    private String address;

    public Station(String name, String address, Coordinates coordinates)
    {
        setName(name);
        setAddress(address);
        setCoordinates(coordinates);
    }

    public Station(long id, String name, String address, Coordinates coordinates)
    {
        this(name, address, coordinates);
        setId(id);
    }

    public void setName(final String name)
    {
        if(isNullOrEmpty(name))
            throw new ValidationException("Name cannot be null.");

        if (name.length() < Constraints.MIN_BUSSTOP_NAME_LENGTH ||
            name.length() > Constraints.MAX_BUSSTOP_NAME_LENGTH)
                throw new ValidationException(
                    String.format("Name must be between %d and %d characters long. Current length: %d",
                            Constraints.MIN_BUSSTOP_NAME_LENGTH,
                            Constraints.MAX_BUSSTOP_NAME_LENGTH,
                            name.length()));
        
        this.name = name.trim().toLowerCase();
    }

    public void setAddress(final String address)
    {
        if(isNullOrEmpty(address))
            throw new ValidationException("Address cannot be null.");

        if (address.length() < Constraints.MIN_BUSSTOP_ADDRESS_LENGTH ||
            address.length() > Constraints.MAX_BUSSTOP_ADDRESS_LENGTH)
                throw new ValidationException(
                    String.format("Address must be between %d and %d characters long. Current length: %d",
                            Constraints.MIN_BUSSTOP_ADDRESS_LENGTH,
                            Constraints.MAX_BUSSTOP_ADDRESS_LENGTH,
                            address.length()));
        
        this.address = address.trim();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var other = (Station) o;
        return id == other.id;
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id);
    }
}
