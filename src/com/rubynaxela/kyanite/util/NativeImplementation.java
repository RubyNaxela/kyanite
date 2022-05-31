package com.rubynaxela.kyanite.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This annotation is purely for informational purposes and provides no actual functionality.
 * Methods annotated by this annotation have native implementation and are only
 * overridden with an empty body to match interfaces implemented by their classes.
 */
@Target(ElementType.METHOD)
public @interface NativeImplementation {

}
