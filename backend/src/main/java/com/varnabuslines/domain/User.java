package com.varnabuslines.domain;

import com.varnabuslines.domain.exceptions.ValidationException;
import com.varnabuslines.domain.utils.EmailValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

@Entity
@Getter
@NoArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String hashedPassword;

    @NotNull
    private Role role;

    @OneToOne
    @JoinColumn
    private BusLine favoriteRoute;

    public User(final String email, final String hashedPassword, final Role role)
    {
        setEmail(email);
        setRole(role);
        setHashedPassword(hashedPassword);
    }

    public User(final long id, final String email, final String hashedPassword, final Role role)
    {
        this(email,hashedPassword, role);
        setId(id);
    }


    public void setFavouriteRoute(final BusLine route)
    {
        if(isNullOrEmpty(route))
            throw new ValidationException("Route cannot be null.");
        favoriteRoute = route;
    }

    public void setEmail(final String email)
    {
        if(isNullOrEmpty(email))
            throw new ValidationException("Email cannot be null.");

        var tempEmail = email.trim();
        if (!EmailValidator.validate(tempEmail))
            throw new ValidationException("Email is not valid: " + email);
        
        this.email = tempEmail;
    }

    public void setId(final long id)
    {
        if (id < 0)
            throw new ValidationException("ID must be positive: " + id);
        
        this.id = id;
    }

    public void setRole(final Role role)
    {
        if(isNullOrEmpty(role))
            throw new ValidationException("Role cannot be null.");
        
        this.role = role;
    }

    public void setHashedPassword(final String hashedPassword)
    {
        if(isNullOrEmpty(hashedPassword))
            throw new ValidationException("Password cannot be null.");
        
        this.hashedPassword = hashedPassword;
    }


    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        var user = (User) o;
        return id == user.id && email.equals(user.email);
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id) + email.hashCode();
    }
}
