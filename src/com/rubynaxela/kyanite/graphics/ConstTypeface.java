package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.Const;

/**
 * Interface for read-only fonts. It provides methods to can gain information from a font,
 * but none to modify it in any way. Note that this interface is expected to be implemented
 * by a {@link Typeface}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstTypeface extends Const {

    /**
     * Gets a glyph information structure from the font.
     *
     * @param unicode       the unicode (UTF-32) of the character to retrieve the glyph for
     * @param characterSize the character size in question
     * @param bold          {@code true} if the bold glyph version should be returned, {@code false} for the regular version
     * @return the {@link Glyph} representing the given unicode character
     */
    Glyph getGlyph(int unicode, int characterSize, boolean bold);

    /**
     * Gets the kerning offset between two glyphs.
     *
     * @param first         the unicode (UTF-32) of the first character
     * @param second        the unicode (UTF-32) of the second character
     * @param characterSize the character size in question
     * @return the kerning offset between the two glyphs
     */
    int getKerning(int first, int second, int characterSize);

    /**
     * Gets the line spacing of the font.
     *
     * @param characterSize the character size in question
     * @return the line spacing of the font
     */
    int getLineSpacing(int characterSize);

    /**
     * Retrieves the texture containing the font's glyphs. The texture returned is immutable.
     *
     * @param characterSize the character size in question
     * @return the texture containing the font's glyphs of the character given size
     */
    ConstTexture getTexture(int characterSize);
}
