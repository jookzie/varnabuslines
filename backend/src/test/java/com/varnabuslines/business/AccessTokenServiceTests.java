package com.varnabuslines.business;

import com.varnabuslines.domain.AccessToken;
import com.varnabuslines.domain.exceptions.InvalidAccessTokenException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@BusinessLayerTest
class AccessTokenServiceTests
{
    private final AccessTokenService service;

    private final AccessToken token;

    private final Pattern jwtRegex;

    public AccessTokenServiceTests()
    {
        var roles = List.of("USER", "ADMIN");
        var userId = 123L;
        token = AccessToken
                .builder()
                .roles(roles)
                .userId(userId)
                .build();

        service = new AccessTokenService("A3J3K5N6P7R9S2J4M5N6Q8R9S3K4M6P7Q8S2J3K5N6P7R9S2K4M5N7Q8R9T3ABEC");

        jwtRegex = Pattern.compile("(^[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*\\.[A-Za-z0-9-_]*$)");
    }

    @Test
    void constructor_pass()
    {
        byte[] b = new byte[32];
        new Random().nextBytes(b);

        assertDoesNotThrow(() -> new AccessTokenService(Arrays.toString(b)));
    }

    @Test
    void constructor_null_still_pass()
    {
        assertDoesNotThrow(() -> new AccessTokenService(null));
    }

    @Test
    void encode_valid_jwt()
    {
        var result = service.encode(token);

        assertTrue(jwtRegex.matcher(result).matches());
    }

    @Test
    void encode_contains_roles_userid()
    {
        var result = service.encode(token);

        var payloadBase64 = result.split("\\.");

        var payload = new String(Base64.getDecoder().decode(payloadBase64[1]));

        assertAll("Should contain roles and userId in the payload",
                () -> assertTrue(payload.contains(token.getRoles().get(0))),
                () -> assertTrue(payload.contains(token.getRoles().get(1))),
                () -> assertTrue(payload.contains(Long.toString(token.getUserId())))
        );
    }

    @Test
    void decode_null()
    {
        assertThrows(InvalidAccessTokenException.class,
                () -> service.decode(null));
    }

    @Test
    void decode_emptyString()
    {
        assertThrows(InvalidAccessTokenException.class,
                () -> service.decode(""));
    }

    @Test
    void decode_invalid_jwt()
    {
        assertThrows(InvalidAccessTokenException.class,
                () -> service.decode("123"));
    }

    @Test
    void decode_pass()
    {
        assertDoesNotThrow(() -> service.decode(service.encode(token)));
    }

    @Test
    void decode_tampered_payload()
    {
        var tokenString = service.encode(token);

        var splitToken = tokenString.split("\\.");

        var payload = splitToken[1];

        // increment first character
        var newPayload = payload.charAt(0) + 1 + payload.substring(1);

        var newToken = splitToken[0] + '.' + newPayload + '.' + splitToken[2];

        assertTrue(jwtRegex.matcher(newToken).matches());

        assertThrows(InvalidAccessTokenException.class,
                () -> service.decode(newToken));
    }

    @Test
    void decode_tampered_key()
    {
        var tokenString = service.encode(token);

        var splitToken = tokenString.split("\\.");

        var key = splitToken[2];

        // increment first character
        var newKey = key.charAt(0) + 1 + key.substring(1);

        var newToken = splitToken[0] + '.' + splitToken[1] + '.' + newKey;

        assertTrue(jwtRegex.matcher(newToken).matches());

        assertThrows(InvalidAccessTokenException.class,
                () -> service.decode(newToken));
    }

}
