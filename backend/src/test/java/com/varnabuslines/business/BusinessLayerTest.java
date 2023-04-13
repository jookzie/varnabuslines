package com.varnabuslines.business;

import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Base meta-annotation for all business logic layer tests
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
@TestPropertySource(locations= "classpath:application-test.properties")
public @interface BusinessLayerTest
{
}
