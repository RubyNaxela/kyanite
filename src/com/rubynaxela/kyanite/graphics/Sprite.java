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
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a drawable instance of a texture or texture portion.
 */
@SuppressWarnings("deprecation")
public class Sprite extends org.jsfml.graphics.Sprite implements Drawable, SceneObject {

    private Color color = Colors.WHITE;
    private IntRect textureRect = IntRect.EMPTY;
    private ConstTexture texture = null;
    private boolean boundsNeedUpdate = true;
    private FloatRect localBounds = null, globalBounds = null;

    /**
     * Constructs a new sprite without a texture.
     */
    public Sprite() {
        super();
    }

    /**
     * Constructs a new sprite with the specified texture.
     *
     * @param texture the texture.
     */
    public Sprite(@NotNull ConstTexture texture) {
        setTexture(texture);
    }

    /**
     * Constructs a new sprite with the specified texture and texture portion.
     *
     * @param texture the texture
     * @param rect    the portion of the texture to use
     */
    public Sprite(@NotNull ConstTexture texture, IntRect rect) {
        this(texture);
        setTextureRect(rect);
    }

    /**
     * Gets the sprite's current color mask.
     *
     * @return the sprite's current color mask
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color mask of the sprite.
     *
     * @param color the new color
     */
    public void setColor(@NotNull Color color) {
        this.color = color;
        nativeSetColor(IntercomHelper.encodeColor(color));
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

    /**
     * Gets the sprite's local bounding rectangle, <i>not</i> taking the sprite's transformation into account.
     *
     * @return the sprite's local bounding rectangle
     * @see Sprite#getGlobalBounds()
     */
    @Override
    public FloatRect getLocalBounds() {
        if (boundsNeedUpdate) updateBounds();
        return localBounds;
    }

    /**
     * Gets the sprite's global bounding rectangle in the scene, taking the sprite's transformation into account.
     *
     * @return the sprite's global bounding rectangle
     * @see Sprite#getLocalBounds()
     */
    @Override
    public FloatRect getGlobalBounds() {
        if (boundsNeedUpdate) updateBounds();
        return globalBounds;
    }

    /**
     * Gets the sprite's current texture.
     *
     * @return the sprite's current texture
     */
    @Override
    public ConstTexture getTexture() {
        return texture;
    }

    /**
     * Sets the texture of this sprite without affecting the texture rectangle.
     *
     * @param texture the new texture
     */
    @Override
    public final void setTexture(@Nullable ConstTexture texture) {
        setTexture(texture, false);
    }

    /**
     * Sets the texture of this sprite.
     *
     * @param texture   the new texture
     * @param resetRect {@code true} to reset the texture rectangle, {@code false} otherwise
     */
    @Override
    public void setTexture(@Nullable ConstTexture texture, boolean resetRect) {
        nativeSetTexture((Texture) texture, resetRect);
        this.texture = texture;
        if (resetRect) textureRect = IntRect.EMPTY;
        boundsNeedUpdate = true;
    }

    /**
     * Gets the sprite's current texture rectangle.
     *
     * @return the sprite's current texture rectangle
     */
    @Override
    public IntRect getTextureRect() {
        return textureRect;
    }

    /**
     * Sets the portion of the texture that will be used for drawing. An empty rectangle can be
     * passed to indicate that the whole texture shall be used. The width and / or height of the
     * rectangle may be negative to indicate that the respective axis should be flipped. For example,
     * a width of {@code -32} will result in a sprite that is 32 pixels wide and flipped horizontally.
     *
     * @param rect the texture portion
     */
    @Override
    public void setTextureRect(@NotNull IntRect rect) {
        this.textureRect = rect;
        nativeSetTextureRect(IntercomHelper.encodeIntRect(rect));
        boundsNeedUpdate = true;
    }

    @Override
    public void setPosition(@NotNull Vector2f v) {
        super.setPosition(v);
        boundsNeedUpdate = true;
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);
        boundsNeedUpdate = true;
    }

    @Override
    public void setScale(@NotNull Vector2f v) {
        super.setScale(v);
        boundsNeedUpdate = true;
    }

    @Override
    public void setOrigin(@NotNull Vector2f v) {
        super.setOrigin(v);
        boundsNeedUpdate = true;
    }

    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        SFMLNativeDrawer.draw(this, target, states);
    }
}
