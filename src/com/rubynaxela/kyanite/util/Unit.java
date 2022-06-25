package com.rubynaxela.kyanite.util;

import java.lang.annotation.*;

/**
 * Indicates that a value marked with this annotation (typically of
 * a primitive type or a vector) is expressed in a certain unit.
 */
@Documented
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unit {

    /**
     * Determines the symbol of the unit marked by this annotation.
     *
     * @return symbol of the unit marked by this annotation
     */
    String value();
}
