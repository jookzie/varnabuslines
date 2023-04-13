package com.varnabuslines.domain.exceptions;

public class InvalidAccessTokenException extends RuntimeException
{
    public InvalidAccessTokenException(final String message)
    {
        super(message);
    }
}
