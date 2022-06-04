package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.IntRect;

/**
 * Describes a glyph in a {@link Typeface}.
 *
 * @param advance     the offset to move horizontally to the next character
 * @param bounds      the bounding rectangle of the glyph, in coordinates relative to the baseline
 * @param textureRect the texture coordinates of the glyph on the font's texture
 * @see Typeface
 */
public record Glyph(int advance, IntRect bounds, IntRect textureRect) {

    /**
     * Constructs a glyph with the specified parameters. Note that this constructor is reserved for internal use and
     * should never be required to be used directly. Glyphs should be obtained using the {@link Typeface#getGlyph} method.
     */
    public Glyph {
    }
}
