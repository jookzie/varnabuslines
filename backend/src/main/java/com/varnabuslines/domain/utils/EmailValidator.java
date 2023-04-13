package com.varnabuslines.domain.utils;


import static com.varnabuslines.domain.utils.ValidationHelper.isNullOrEmpty;

public class EmailValidator
{
    public static boolean validate(final String email)
    {
        if(isNullOrEmpty(email))
            return false;

        return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
    }

    private EmailValidator()
    {
    }
}
