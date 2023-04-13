package com.varnabuslines.domain.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

public class PasswordHasher
{
    private static final PasswordEncoder encoder = new Argon2PasswordEncoder();

    public static String hash(final String password)
    {
        if(isNullOrEmpty(password))
            throw new PasswordHashingException("Password cannot be null or empty.");

        return encoder.encode(password);
    }

    public static boolean verify(final String password, final String hashedPassword)
    {
        if(isNullOrEmpty(password))
            throw new PasswordHashingException("Password cannot be null or empty.");

        if(isNullOrEmpty(hashedPassword))
            throw new PasswordHashingException("Hashed password cannot be null or empty.");

        return encoder.matches(password, hashedPassword);
    }

    public static class PasswordHashingException extends RuntimeException
    {
        public PasswordHashingException(final String message)
        {
            super(message);
        }
    }

    private PasswordHasher()
    {
    }
}
