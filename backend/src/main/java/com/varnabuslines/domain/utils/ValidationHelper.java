package com.varnabuslines.domain.utils;

public class ValidationHelper
{
    public static boolean isNullOrEmpty(final Object value)
    {
        if(value == null)
        {
            return true;
        }
        else if(value instanceof String str)
        {
            return str.trim().isEmpty();
        }
        else
        {
            return false;
        }

    }

    private ValidationHelper()
    {
    }
}
