package com.varnabuslines.business;

import com.varnabuslines.domain.Role;
import com.varnabuslines.domain.User;
import com.varnabuslines.domain.utils.PasswordHasher;
import com.varnabuslines.persistence.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@BusinessLayerTest
class AuthenticationServiceTest
{
    private static final String password = "password";
    private static final User mockUser = new User(
            1,
            "test@test.com",
            PasswordHasher.hash(password),
            Role.USER);

    private static final UserRepo mockUserRepo = mock(UserRepo.class);
    private static final AccessTokenService mockAccessTokenService = mock(AccessTokenService.class);
    private static final AuthenticationService service = new AuthenticationService(mockUserRepo, mockAccessTokenService);

    @BeforeEach
    void setup()
    {
        when(mockUserRepo.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(mockAccessTokenService.encode(any())).thenReturn("token");
    }

    @Test
    void authenticate_valid()
    {
        assertTrue(service.authenticate(mockUser.getEmail(), password).isPresent());
    }

    @Test
    void authenticate_invalid()
    {
        assertTrue(service
                .authenticate(
                    mockUser.getEmail(),
                password + anyChar())
                .isEmpty());
    }

    @Test
    void authenticate_unknown_account()
    {
        assertTrue(service.authenticate(mockUser.getEmail() + anyChar(), password).isEmpty());
    }
}