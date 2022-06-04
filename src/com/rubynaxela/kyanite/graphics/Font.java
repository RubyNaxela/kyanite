package com.rubynaxela.kyanite.graphics;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

/**
 * Holds a {@link Typeface} along with the font style and the character size for use in text displays.
 */
public class Font {

    private final ConstTypeface typeface;
    private final int size, style;
    private final boolean antialiasing;

    /**
     * Creates a new font.
     *
     * @param typeface     the font face
     * @param size         the character size
     * @param style        the font style
     * @param antialiasing {@code true} to enable font antialiasing; {@code false} to disable
     */
    public Font(@NotNull Typeface typeface, int size,
                @MagicConstant(flagsFromClass = FontStyle.class) int style, boolean antialiasing) {
        this.typeface = typeface;
        this.size = size;
        this.style = style;
        this.antialiasing = antialiasing;
    }

    /**
     * Creates a new font. Font antialiasing is enabled.
     *
     * @param typeface the font face
     * @param size     the character size
     * @param style    the font style
     */
    public Font(@NotNull Typeface typeface, int size, @MagicConstant(flagsFromClass = FontStyle.class) int style) {
        this(typeface, size, style, true);
    }

    /**
     * Creates a new font with default style.
     *
     * @param typeface     the font face
     * @param size         the character size
     * @param antialiasing {@code true} to enable font antialiasing; {@code false} to disable
     */
    public Font(@NotNull Typeface typeface, int size, boolean antialiasing) {
        this(typeface, size, FontStyle.REGULAR, antialiasing);
    }

    /**
     * Creates a new font with default style. Font antialiasing is enabled.
     *
     * @param typeface the font face
     * @param size     the character size
     */
    public Font(@NotNull Typeface typeface, int size) {
        this(typeface, size, FontStyle.REGULAR, true);
    }

    /**
     * @return the font face
     */
    public ConstTypeface getTypeface() {
        return typeface;
    }

    /**
     * @return the character size of this font
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the style of this font
     */
    @MagicConstant(flagsFromClass = FontStyle.class)
    public int getStyle() {
        return style;
    }

    /**
     * Whether antialiasing is enabled for this font.
     *
     * @return {@code true} if antialiasing is enabled; {@code false} otherwise
     */
    public boolean antialiasingEnabled() {
        return antialiasing;
    }
}
