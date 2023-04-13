package com.varnabuslines.domain;

import com.varnabuslines.domain.exceptions.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    private float price;

    @NotNull
    private int duration;

    public Ticket(float price, int duration)
    {
        setPrice(price);
        setDuration(duration);
    }

    public Ticket(long id, float price, int duration)
    {
        this(price, duration);
        setId(id);
    }

    public void setPrice(final float price)
    {
        if (price <= 0)
            throw new ValidationException(
                    String.format("Price must be a positive number. Current value: '%f'.", price));
        this.price = price;
    }

    public void setDuration(final int duration)
    {
        if (duration <= 0)
            throw new ValidationException(
                    String.format("Duration must be a positive number. Current value: %s.", duration));
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var other = (Ticket) o;
        return id == other.id;
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id);
    }
}
