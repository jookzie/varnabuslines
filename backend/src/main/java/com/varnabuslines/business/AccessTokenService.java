package com.varnabuslines.business;

import com.varnabuslines.domain.AccessToken;
import com.varnabuslines.domain.exceptions.InvalidAccessTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

@Service
@Slf4j
public class AccessTokenService
{
    private final Key key;

    public AccessTokenService(@Value("${jwt.secret:#{null}}") final String secret)
    {
        String key = secret;

        if(isNullOrEmpty(secret))
        {
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[32];
            random.nextBytes(bytes);
            key = new String(bytes);
            log.warn("No JWT_SECRET specified in application.properties. Proceeding with a random key: {}", key);
        }

        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String encode(final AccessToken accessToken)
    {
        if(isNullOrEmpty(accessToken))
            throw new InvalidAccessTokenException("Access token cannot be null.");

        Map<String, Object> claimsMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(accessToken.getRoles()))
        {
            claimsMap.put("roles", accessToken.getRoles());
        }
        claimsMap.put("userId", accessToken.getUserId());

        var now = Instant.now();
        return Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    public AccessToken decode(final String accessTokenEncoded)
    {
        if(isNullOrEmpty(accessTokenEncoded))
            throw new InvalidAccessTokenException("Access token cannot be null or empty.");

        try
        {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessTokenEncoded)
                    .getBody();
            List<String> roles = claims.get("roles", List.class);

            return AccessToken.builder()
                    .roles(roles)
                    .userId(claims.get("userId", Long.class))
                    .build();

        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }
}

