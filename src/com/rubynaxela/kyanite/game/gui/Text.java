package com.rubynaxela.kyanite.game.gui;

import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a graphical text that can be transformed and drawn to a render target.
 */
public class Text extends org.jsfml.graphics.Text {

    private Alignment alignment = Alignment.TOP_LEFT;

    /**
     * Creates a new empty text.
     */
    public Text() {
        super();
    }

    /**
     * Creates a new empty text.
     *
     * @param font the font face to use
     */
    public Text(@NotNull Font font) {
        super();
        setFont(font);
    }

    /**
     * Creates an invisible text with no font.
     *
     * @param text the text string
     */
    public Text(@NotNull String text) {
        super();
        super.setString(text);
    }

    /**
     * Creates a text.
     *
     * @param text the text string
     * @param font the font face to use
     */
    public Text(@NotNull String text, @NotNull Font font) {
        super();
        super.setString(text);
        setFont(font);
    }

    /**
     * Sets the font of this text.
     *
     * @param font the new font of this text
     */
    public void setFont(@NotNull Font font) {
        font.apply(this);
    }

    /**
     * @deprecated Use the {@link Text#setText} method instead.
     */
    @Deprecated
    @Override
    public void setString(@NotNull String text) {
        throw new UnsupportedOperationException("Use the Text#setText method instead.");
    }

    /**
     * Sets the string to display.
     *
     * @param text the string to display
     */
    public void setText(@NotNull String text) {
        super.setString(text);
        updateOrigin();
    }

    /**
     * @return the alignment mode of this text
     */
    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the alignment mode of this text. The default value is {@code TOP_LEFT}.
     * This does not properly align multiline texts. For proper multiline text alignment,
     * build a {@link CompoundEntity} consisting of multiple {@code Text} objects.
     *
     * @param alignment the new alignment mode of this text
     */
    public void setAlignment(@NotNull Alignment alignment) {
        this.alignment = alignment;
        updateOrigin();
    }

    private void updateOrigin() {
        final float width = getGlobalBounds().width, height = getGlobalBounds().height;
        switch (alignment) {
            case TOP_LEFT -> setOrigin(0, 0);
            case TOP_CENTER -> setOrigin(width / 2, 0);
            case TOP_RIGHT -> setOrigin(width, 0);
            case CENTER_LEFT -> setOrigin(0, height / 2);
            case CENTER -> setOrigin(width / 2, height / 2);
            case CENTER_RIGHT -> setOrigin(width, height / 2);
            case BOTTOM_LEFT -> setOrigin(0, height);
            case BOTTOM_CENTER -> setOrigin(width / 2, height);
            case BOTTOM_RIGHT -> setOrigin(width, height);
        }
    }

    /**
     * Values for the alignment mode. The first word of the name indicates vertical alignment and the second
     * word indicates horizontal alignment. The {@code CENTER} value indicates centering on both axes.
     *
     * @see Text#setAlignment
     */
    public enum Alignment {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT, CENTER_LEFT, CENTER, CENTER_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }
}
