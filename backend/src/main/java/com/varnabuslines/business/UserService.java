package com.varnabuslines.business;

import com.varnabuslines.domain.Constraints;
import com.varnabuslines.domain.Role;
import com.varnabuslines.domain.User;
import com.varnabuslines.domain.exceptions.ValidationException;
import com.varnabuslines.domain.utils.PasswordHasher;
import com.varnabuslines.persistence.BusLineRepo;
import com.varnabuslines.persistence.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

@AllArgsConstructor
@Service
public class UserService
{
    private final UserRepo userRepo;
    private final BusLineRepo busLineRepo;

    public User createUser(final String email, final String password)
    {
        validatePassword(password);
        User user = new User();
        user.setEmail(email);
        user.setHashedPassword(PasswordHasher.hash(password));
        user.setRole(Role.USER);
        return userRepo.save(user);
    }

    public User createAdmin(final String email, final String password)
    {
        validatePassword(password);
        User user = new User();
        user.setEmail(email);
        user.setHashedPassword(PasswordHasher.hash(password));
        user.setRole(Role.ADMIN);
        return userRepo.save(user);
    }

    public Iterable<User> get()
    {
        return userRepo.findAll();
    }


    public Optional<User> get(final long id)
    {
        var opt = userRepo.findById(id);
        if (opt.isEmpty())
            return Optional.empty();

        var user = opt.get();
        return Optional.of(user);
    }

    public Optional<User> updateAsUser(final long id, final String email, final String password)
    {
        validatePassword(password);
        var opt = userRepo.findById(id);
        if (opt.isEmpty())
            return Optional.empty();

        var user = opt.get();

        user.setEmail(email);
        user.setHashedPassword(PasswordHasher.hash(password));
        return Optional.of(userRepo.save(user));
    }

    public Optional<User> updateAsAdmin(final long id, final String email, final String password, final Role role)
    {
        validatePassword(password);
        var opt = userRepo.findById(id);
        if (opt.isEmpty())
            return Optional.empty();

        var user = opt.get();

        // Check if there is at least one admin after update
        if(role == Role.USER && user.getRole() == Role.ADMIN && userRepo.findAllByRole(Role.ADMIN).size() == 1)
            throw new IllegalStateException("There must be at least one admin");

        user.setEmail(email);
        user.setHashedPassword(PasswordHasher.hash(password));
        user.setRole(role);
        return Optional.of(userRepo.save(user));
    }


    public void setFavouriteRoute(final long userId, final String routeId)
    {
        var userOpt = get(userId);
        var busLineOpt = busLineRepo.findById(routeId);

        if (userOpt.isEmpty() || busLineOpt.isEmpty())
            return;

        var user = userOpt.get();
        var busLine = busLineOpt.get();

        user.setFavouriteRoute(busLine);
        userRepo.save(user);
    }


    public void delete(final long id)
    {
        var admins = userRepo.findAllByRole(Role.ADMIN);

        // Check if there is at least one admin after delete
        if(admins.size() == 1 && admins.get(0).getId() == id)
            throw new IllegalStateException("Cannot delete the last admin.");

        userRepo.deleteById(id);
    }

    private void validatePassword(final String password)
    {
        if(isNullOrEmpty(password))
            throw new ValidationException("Password cannot be null or empty.");

        var length = password.length();
        if (length < Constraints.MIN_PASSWORD_LENGTH || length > Constraints.MAX_PASSWORD_LENGTH)
            throw new ValidationException(
                    "Password should be between " + Constraints.MIN_PASSWORD_LENGTH +
                    " and " + Constraints.MAX_PASSWORD_LENGTH + " characters long.");
    }

}
