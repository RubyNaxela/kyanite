package com.rubynaxela.kyanite.graphics;

/**
 * Provides font style constants. These constants can be combined
 * using an arithmetic {@code OR} operation to define a font style.
 */
public interface FontStyle {

    /**
     * Regular drawing style.
     */
    int REGULAR = 0;
    /**
     * Bold drawing style.
     */
    int BOLD = 0x01;
    /**
     * Italic drawing style.
     */
    int ITALIC = 0x02;
    /**
     * Underlined drawing style.
     */
    int UNDERLINED = 0x04;
}
