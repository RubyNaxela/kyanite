/*
 * Copyright (c) 2021-2022 Alex Pawelski
 *
 * Licensed under the Silicon License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://rubynaxela.github.io/Silicon-License/plain_text.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vector2f;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a graphical text that can be transformed and drawn to a render target. This class
 * implements the {@code TextStyle} interface for quick access to the constants provided by it.
 */
@SuppressWarnings("deprecation")
public class Text extends org.jsfml.graphics.Text implements Drawable, FontStyle, BoundsObject {

    private static final Font DEFAULT_FONT = new Font(Typeface.JETBRAINS_MONO, 32, REGULAR);
    private Font font;
    private String text;
    private Color color = Colors.WHITE;
    private boolean boundsNeedUpdate = true;
    private FloatRect localBounds = null, globalBounds = null;
    private Alignment alignment = Alignment.TOP_LEFT;
    private int layer = 0;

    /**
     * Creates a new empty text with default font ({@link Typeface#JETBRAINS_MONO}, 32px, regular).
     */
    public Text() {
        this("", DEFAULT_FONT);
    }

    /**
     * Creates a new empty text with the specified font.
     *
     * @param font the font face to use
     */
    public Text(@NotNull Font font) {
        this("", font);
    }

    /**
     * Creates a new text with the specified string and default font ({@link Typeface#JETBRAINS_MONO}, 32px, regular).
     *
     * @param text the text string
     */
    public Text(@NotNull String text) {
        this(text, DEFAULT_FONT);
    }

    /**
     * Creates a new text.
     *
     * @param text the text string
     * @param font the font to use
     */
    public Text(@NotNull String text, @NotNull Font font) {
        setFont(font);
        setText(text);
    }

    /**
     * Gets the text's string.
     *
     * @return the text strng
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the string to display.
     *
     * @param string the string to display
     */
    public void setText(String string) {
        this.text = Objects.requireNonNull(string);
        nativeSetString(string);
        boundsNeedUpdate = true;
        updateOrigin();
    }

    /**
     * Gets the text's current font.
     *
     * @return The text's current font (may be {@code null} if no font has been set yet)
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the text's font.
     *
     * @param font the text's font
     */
    public void setFont(@NotNull Font font) {
        this.font = Objects.requireNonNull(font);
        nativeSetFont((Typeface) font.getTypeface());
        setCharacterSize(font.getSize());
        setStyle(font.getStyle());
        boundsNeedUpdate = true;
        updateOrigin();
    }

    /**
     * Gets the text's current typeface.
     *
     * @return the text's current typeface
     */
    public ConstTypeface getTypeface() {
        return font.getTypeface();
    }

    /**
     * Sets the typeface for this text.
     *
     * @param typeface the typeface for this text
     */
    public void setTypeface(@NotNull ConstTypeface typeface) {
        setTypeface(typeface, true);
    }

    /**
     * Sets the typeface for this text.
     *
     * @param typeface     the typeface for this text
     * @param antialiasing {@code true} to enable font antialiasing; {@code false} to disable
     */
    public void setTypeface(@NotNull ConstTypeface typeface, boolean antialiasing) {
        nativeSetFont((Typeface) typeface);
        font = new Font((Typeface) typeface, font.getSize(), font.getStyle(), antialiasing);
        boundsNeedUpdate = true;
        updateOrigin();
    }

    /**
     * Gets the text's current font size.
     *
     * @return the text's current font size
     */
    public int getCharacterSize() {
        return font.getSize();
    }

    /**
     * Sets the font size for this text.
     *
     * @param characterSize the font size for this text
     */
    public void setCharacterSize(int characterSize) {
        nativeSetCharacterSize(characterSize);
        font = new Font((Typeface) font.getTypeface(), characterSize, font.getStyle(), font.antialiasingEnabled());
        ((Texture) font.getTypeface().getTexture(characterSize)).setSmooth(font.antialiasingEnabled());
        boundsNeedUpdate = true;
        updateOrigin();
    }

    /**
     * Gets the text's current font style.
     *
     * @return the text's current font style
     * @see Text#setStyle(int)
     */
    public int getStyle() {
        return font.getStyle();
    }

    /**
     * Sets the font drawing style.
     *
     * @param style the font drawing style
     */
    public void setStyle(@MagicConstant(flagsFromClass = FontStyle.class) int style) {
        nativeSetStyle(style);
        font = new Font((Typeface) font.getTypeface(), font.getSize(), style, font.antialiasingEnabled());
        boundsNeedUpdate = true;
        updateOrigin();
    }

    /**
     * Gets the text's current font color.
     *
     * @return the text's current font color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the font color for this text.
     *
     * @param color the font color for this text
     */
    public void setColor(Color color) {
        nativeSetColor(IntercomHelper.encodeColor(color));
        this.color = color;
    }

    /**
     * @return the alignment mode of this text
     */
    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the alignment mode of this text. The default value is {@code TOP_LEFT}. This does
     * not properly align multiline texts in horizontal direction. For proper multiline text
     * alignment, build a {@link CompoundEntity} consisting of multiple {@code Text} objects.
     *
     * @param alignment the new alignment mode of this text
     */
    public void setAlignment(@NotNull Alignment alignment) {
        this.alignment = alignment;
        updateOrigin();
    }

    /**
     * Returns the position of a character inside the string.
     *
     * @param i the index of the character to return the position for
     * @return the position of the character at the given index
     */
    public Vector2f findCharacterPos(int i) {
        if (i < 0 || i >= text.length()) throw new StringIndexOutOfBoundsException(Integer.toString(i));
        return IntercomHelper.decodeVector2f(nativeFindCharacterPos(i));
    }

    private void updateBounds() {
        if (boundsNeedUpdate) {
            nativeGetLocalBounds(IntercomHelper.getBuffer());
            localBounds = IntercomHelper.decodeFloatRect();
            nativeGetGlobalBounds(IntercomHelper.getBuffer());
            globalBounds = IntercomHelper.decodeFloatRect();
            boundsNeedUpdate = false;
        }
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
        boundsNeedUpdate = true;
    }

    /**
     * Gets the text's local bounding rectangle, <i>not</i> taking the text's transformation into account.
     *
     * @return the text's local bounding rectangle
     * @see Sprite#getGlobalBounds()
     */
    @Override
    public FloatRect getLocalBounds() {
        if (boundsNeedUpdate) updateBounds();
        return localBounds;
    }

    /**
     * Gets the text's global bounding rectangle in the scene, taking the text's transformation into account.
     *
     * @return the text's global bounding rectangle
     * @see Text#getLocalBounds()
     */
    @Override
    public FloatRect getGlobalBounds() {
        if (boundsNeedUpdate) updateBounds();
        return globalBounds;
    }

    @Override
    public void setPosition(@NotNull Vector2f position) {
        super.setPosition(position);
        boundsNeedUpdate = true;
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);
        boundsNeedUpdate = true;
    }

    @Override
    public void setScale(@NotNull Vector2f scale) {
        super.setScale(scale);
        boundsNeedUpdate = true;
    }

    @Override
    public void setOrigin(@NotNull Vector2f origin) {
        super.setOrigin(origin);
        boundsNeedUpdate = true;
    }

    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        SFMLNativeDrawer.draw(this, target, states);
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }
}
