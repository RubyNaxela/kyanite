package com.rubynaxela.kyanite.graphics;

/**
 * Values for the alignment mode. The first word of the name indicates vertical alignment and the
 * second word indicates horizontal alignment. The {@code CENTER} value indicates centering on both axes.
 * The alignment determines where (relative to the text) is the origin point of that text object.
 *
 * @see Text#setAlignment
 */
public enum Alignment {
    /**
     * Horizontal alignment: left; vertical alignment: top.
     */
    TOP_LEFT,
    /**
     * Horizontal alignment: center; vertical alignment: top.
     */
    TOP_CENTER,
    /**
     * Horizontal alignment: right; vertical alignment: top.
     */
    TOP_RIGHT,
    /**
     * Horizontal alignment: left; vertical alignment: center.
     */
    CENTER_LEFT,
    /**
     * Horizontal alignment: center; vertical alignment: center.
     */
    CENTER,
    /**
     * Horizontal alignment: right; vertical alignment: center.
     */
    CENTER_RIGHT,
    /**
     * Horizontal alignment: left; vertical alignment: bottom.
     */
    BOTTOM_LEFT,
    /**
     * Horizontal alignment: center; vertical alignment: bottom.
     */
    BOTTOM_CENTER,
    /**
     * Horizontal alignment: right; vertical alignment: bottom.
     */
    BOTTOM_RIGHT
}
