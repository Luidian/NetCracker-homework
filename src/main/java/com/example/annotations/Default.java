package com.example.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author  Alexanrd Spaskin
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Default {
}
