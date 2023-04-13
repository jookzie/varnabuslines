package com.varnabuslines.domain;

import com.varnabuslines.domain.exceptions.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

@Entity
@NoArgsConstructor
public class BusLine
{
    @Id
    @Getter
    @NotNull
    private String id;

    @ManyToMany
    private List<Station> stations = new ArrayList<>();

    @OneToMany
    private Set<Bus> buses = new HashSet<>();

    @ManyToMany
    private Set<Ticket> tickets = new HashSet<>();

    @Getter
    private boolean available;

    public BusLine(String id)
    {
        setId(id);
        setAvailable(true);
    }

    public Set<Bus> getBuses()
    {
        return Set.copyOf(buses);
    }

    public List<Station> getStations()
    {
        return List.copyOf(stations);
    }

    public Set<Ticket> getTickets()
    {
        return Set.copyOf(tickets);
    }

    public void setId(final String id)
    {
        if(isNullOrEmpty(id))
            throw new ValidationException("Id cannot be null.");
        
        if (id.length() > Constraints.MAX_BUSLINE_ID_LENGTH ||
            id.length() < Constraints.MIN_BUSLINE_ID_LENGTH)
                throw new ValidationException(
                    String.format("Id length must be between %d and %d characters long." +
                                    " Given: %d",
                            Constraints.MAX_BUSLINE_ID_LENGTH,
                            Constraints.MIN_BUSLINE_ID_LENGTH,
                            id.length()));
        
        var pattern = Pattern.compile("^[a-zA-Z\\d]+");
        if (!pattern.matcher(id).matches())
            throw new ValidationException(
                    "Id can only contain numbers and letters. " +
                    "Given: " + id);
        
        this.id = id;
    }

    public void setAvailable(final Boolean available)
    {
        if(isNullOrEmpty(available))
            throw new ValidationException("Bus availability cannot be null.");
        
        this.available = available;
    }

    public void setStations(final List<Station> stations)
    {
        if(isNullOrEmpty(stations))
            throw new ValidationException("Stations list cannot be null.");

        this.stations = stations;
    }

    public void setBuses(final Set<Bus> buses)
    {
        if(isNullOrEmpty(buses))
            throw new ValidationException("Buses list cannot be null.");

        this.buses = buses;
    }

    public void setTickets(final Set<Ticket> tickets)
    {
        if(isNullOrEmpty(tickets))
            throw new ValidationException("Tickets list cannot be null.");

        this.tickets = tickets;
    }

    public boolean lineAvailable()
    {
        return available && !buses.isEmpty() && buses.stream().anyMatch(Bus::isAvailable);
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var other = (BusLine) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
}
