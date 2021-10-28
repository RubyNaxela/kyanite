package com.rubynaxela.kyanite.util;

import java.lang.annotation.*;

/**
 * Indicates that the annotated {@code String} is an asset identifier. The programmer can use
 * this annotation to enhance code readability. It does not have any uses other than that.
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface AssetId {

}
