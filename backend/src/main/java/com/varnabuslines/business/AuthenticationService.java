package com.varnabuslines.business;

import com.varnabuslines.domain.AccessToken;
import com.varnabuslines.domain.User;
import com.varnabuslines.domain.utils.PasswordHasher;
import com.varnabuslines.persistence.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationService
{
    private final UserRepo userRepo;

    private AccessTokenService accessTokenService;

    public Optional<String> authenticate(final String email, final String password)
    {
        var opt = userRepo.findByEmail(email);
        if (opt.isEmpty())
            return Optional.empty();

        var user = opt.get();

        if (!PasswordHasher.verify(password, user.getHashedPassword()))
            return Optional.empty();

        return Optional.of(generateAccessToken(user));
    }

    private String generateAccessToken(final User user) {

        var role = user.getRole().toString();
        return accessTokenService.encode(
                AccessToken.builder()
                        .roles(List.of(role))
                        .userId(user.getId())
                        .build());
    }
}
