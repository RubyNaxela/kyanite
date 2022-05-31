package com.rubynaxela.kyanite.game.gui;

import com.rubynaxela.kyanite.game.assets.FontFace;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.graphics.Text;
import com.rubynaxela.kyanite.graphics.TextStyle;

/**
 * Holds a font face along with the font style and the character size for use in text displays.
 *
 * @see FontFace
 */
@SuppressWarnings("ClassCanBeRecord")
public class Font {

    private final FontFace fontFace;
    private final int size, style;

    /**
     * Creates a new font.
     *
     * @param fontFace the font face
     * @param size     the character size
     * @param style    the font style
     */
    public Font(@NotNull FontFace fontFace, int size, @MagicConstant(flagsFromClass = TextStyle.class) int style) {
        this.fontFace = fontFace;
        this.size = size;
        this.style = style;
    }

    /**
     * Creates a new font with default style.
     *
     * @param fontFace the font face
     * @param size     the character size
     */
    public Font(@NotNull FontFace fontFace, int size) {
        this(fontFace, size, TextStyle.REGULAR);
    }

    /**
     * @return the font face
     */
    public FontFace getFontFace() {
        return fontFace;
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
    @MagicConstant(flagsFromClass = TextStyle.class)
    public int getStyle() {
        return style;
    }

    /**
     * Applies this font on a {@link Text} object.
     *
     * @param text the {@link Text} object to apply this font on
     */
    public void apply(@NotNull Text text) {
        fontFace.apply(text);
        text.setCharacterSize(size);
        text.setStyle(style);
    }
}
