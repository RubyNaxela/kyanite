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

import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Defines a set of methods for centering, manipulating an object's texture and computing bounds.
 */
public interface SceneObject extends BoundsObject {

    /**
     * Returns whether this {@code SceneObject} is set to be centered.
     *
     * @return {@code true} if this {@code SceneObject} is set to be centered, {@code false} otherwise.
     */
    boolean isCentered();

    /**
     * Sets whether this {@code SceneObject} has to be centered by keeping its origin at the center of its local bounds.
     *
     * @param centered {@code true} to keep this {@code SceneObject} centered,
     *                 {@code false} to reset the origin to the point (0,0)
     */
    void setCentered(boolean centered);

    /**
     * Gets the object's current texture.
     *
     * @return the object's current texture
     */
    ConstTexture getTexture();

    /**
     * Sets the texture of the object without affecting the texture rectangle.
     * The texture may be {@code null} if no texture is to be used.
     *
     * @param texture the texture of the object, or {@code null} to indicate that no texture is to be used
     */
    void setTexture(@Nullable ConstTexture texture);

    /**
     * Sets the animated texture of the object without affecting the texture rectangle.
     * The texture may be {@code null} if no animated texture is to be used.
     *
     * @param texture the animated texture of the object, or {@code null} to indicate that no texture is to be used
     */
    void setTexture(@Nullable ConstAnimatedTexture texture);

    /**
     * Sets the texture of the object. The texture may be {@code null} if no texture is to be used.
     *
     * @param texture   the texture of the object, or {@code null} to indicate that no texture is to be used
     * @param resetRect {@code true} to reset the texture rect, {@code false} otherwise
     */
    void setTexture(@Nullable ConstTexture texture, boolean resetRect);

    /**
     * Sets the animated texture of the object. The texture may be {@code null} if no texture is to be used.
     *
     * @param texture   the animated texture of the object, or {@code null} to indicate that no texture is to be used
     * @param resetRect {@code true} to reset the texture rect, {@code false} otherwise
     */
    void setTexture(@Nullable ConstAnimatedTexture texture, boolean resetRect);

    /**
     * Gets the object's current animated texture.
     *
     * @return the object's current animated texture
     */
    ConstAnimatedTexture getAnimatedTexture();

    /**
     * Updates this texture for this object. This method is run by the scene loop and does not need to be invoked manualy.
     *
     * @param elapsedTime the time since the game started
     */
    void updateAnimatedTexture(@NotNull Time elapsedTime);

    /**
     * Gets the object's current texture portion.
     *
     * @return the object's current texture portion
     */
    IntRect getTextureRect();

    /**
     * Sets the portion of the texture that will be used for drawing. An empty rectangle can be
     * passed to indicate that the whole texture shall be used. The width and / or height of the
     * rectangle may be negative to indicate that the respective axis should be flipped. For example,
     * a width of {@code -32} will result in a sprite that is 32 pixels wide and flipped horizontally.
     *
     * @param rect the texture portion
     */
    void setTextureRect(@NotNull IntRect rect);
}
