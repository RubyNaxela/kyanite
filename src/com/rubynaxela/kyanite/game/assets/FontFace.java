package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.game.gui.Font;
import com.rubynaxela.kyanite.game.gui.Text;
import com.rubynaxela.kyanite.graphics.ConstFont;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.rubynaxela.kyanite.graphics.Texture;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A wrapper class for JSFML {@link com.rubynaxela.kyanite.graphics.Font} objects, representing a font
 * face asset. The source must be the path or an {@link InputStream} to an font file. Supported
 * formats are: TrueType, Type 1, CFF, OpenType, SFNT, X11 PCF, Windows FNT, BDF, PFR and Type 42.
 *
 * @see Font
 */
public class FontFace implements Asset {

    /**
     * JetBrains Mono, a built-in font face.
     */
    public static final FontFace JETBRAINS_MONO = new FontFace(
            Objects.requireNonNull(FontFace.class.getResourceAsStream("/res/jetbrains_mono.ttf")));

    private final com.rubynaxela.kyanite.graphics.Font font;

    private FontFace(@NotNull com.rubynaxela.kyanite.graphics.Font font) {
        this.font = font;
    }

    /**
     * Creates a new font from the source specified by the path.
     *
     * @param path path to the font file
     * @throws IOException in case an I/O error occurs
     */
    public FontFace(@NotNull Path path) {
        font = new com.rubynaxela.kyanite.graphics.Font();
        font.loadFromFile(path);
    }

    /**
     * Creates a new font from the source specified by the path.
     *
     * @param path path to the font file
     * @throws IOException in case an I/O error occurs
     */
    public FontFace(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new font from the source font file.
     *
     * @param file the font file
     * @throws IOException in case an I/O error occurs
     */
    public FontFace(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new font from the input stream.
     *
     * @param stream the font data input stream
     * @throws IOException in case an I/O error occurs
     */
    public FontFace(@NotNull InputStream stream) {
        font = new com.rubynaxela.kyanite.graphics.Font();
        font.loadFromStream(stream);
    }

    /**
     * @param text a {@link Text} object
     * @return the font that has been applied to the specified text object
     */
    @Nullable
    public static FontFace of(@NotNull Text text) {
        final ConstFont font = text.getFont();
        return font != null ? new FontFace((com.rubynaxela.kyanite.graphics.Font) font) : null;
    }

    /**
     * Applies this font on the {@link com.rubynaxela.kyanite.graphics.Text}.
     *
     * @param text a {@link com.rubynaxela.kyanite.graphics.Text} to apply this font on
     */
    public void apply(@NotNull com.rubynaxela.kyanite.graphics.Text text) {
        text.setFont(font);
    }

    /**
     * Disables anti-aliasing for the specified character size. Useful when a font appears blurry.
     *
     * @param characterSize the character size for which anti-aliasing of this font will be disabled
     */
    public void disableAntialiasing(int characterSize) {
        ((Texture) font.getTexture(characterSize)).setSmooth(false);
    }
}
