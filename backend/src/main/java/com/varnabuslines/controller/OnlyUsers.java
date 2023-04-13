package com.varnabuslines.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


// Restricts users to access *only* their account
@Retention(RUNTIME)
@Target(ElementType.METHOD)
@PreAuthorize("hasRole('ROLE_ADMIN') || principal.username == #id.toString()")
public @interface OnlyUsers
{
}
