package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.game.gui.Font;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A wrapper class for JSFML {@link org.jsfml.graphics.Font} objects, representing
 * a font face asset. The source must be the path to an font file. Supported formats
 * are: TrueType, Type 1, CFF, OpenType, SFNT, X11 PCF, Windows FNT, BDF, PFR and Type 42.
 *
 * @see Font
 */
public class FontFace implements Asset {

    public static final FontFace JETBRAINS_MONO = new FontFace(
            Objects.requireNonNull(FontFace.class.getResourceAsStream("/res/jetbrains_mono.ttf")));

    private final org.jsfml.graphics.Font font;

    /**
     * Creates a new font from the source specified by the path.
     *
     * @param path path to the font file
     */
    public FontFace(@NotNull Path path) {
        font = new org.jsfml.graphics.Font();
        try {
            font.loadFromFile(path);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Creates a new font from the source specified by the path.
     *
     * @param path path to the font file
     */
    public FontFace(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new font from the source font file.
     *
     * @param file the font file
     */
    public FontFace(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new font from the input stream.
     *
     * @param stream the font data input stream
     */
    public FontFace(@NotNull InputStream stream) {
        font = new org.jsfml.graphics.Font();
        try {
            font.loadFromStream(stream);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Applies this font on the {@link org.jsfml.graphics.Text}.
     *
     * @param text a {@link org.jsfml.graphics.Text} to apply this font on
     */
    public void apply(@NotNull org.jsfml.graphics.Text text) {
        text.setFont(font);
    }
}
