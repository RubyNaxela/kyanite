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
import com.rubynaxela.kyanite.data.Pair;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Abstract base class for (optionally) textured shapes with (optional) outlines.
 */
@SuppressWarnings("deprecation")
public abstract class Shape extends org.jsfml.graphics.Shape implements Drawable, SceneObject {

    protected Vector2f[] points = null;
    protected boolean pointsNeedUpdate = true, boundsNeedUpdate = true, keepCentered = false;
    private Color fillColor = Colors.WHITE, outlineColor = Colors.WHITE;
    private float outlineThickness = 0;
    private IntRect textureRect = IntRect.EMPTY;
    private ConstTexture texture = null;
    private ConstAnimatedTexture animatedTexture = null;
    private FloatRect localBounds = null, globalBounds = null;
    private int layer = 0;

    /**
     * Gets the shape's current fill color.
     *
     * @return the shape's current fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color of the shape.
     *
     * @param color the new fill color of the shape, or {@link Colors#TRANSPARENT}
     *              to indicate that the shape should not be filled
     */
    public void setFillColor(@NotNull Color color) {
        nativeSetFillColor(IntercomHelper.encodeColor(color));
        this.fillColor = color;
    }

    /**
     * Gets the shape's current outline color.
     *
     * @return the shape's current outline color
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * Sets the outline color of the shape.
     *
     * @param color the new outline color of the shape
     */
    public void setOutlineColor(@NotNull Color color) {
        nativeSetOutlineColor(IntercomHelper.encodeColor(color));
        this.outlineColor = color;
    }

    /**
     * Gets the shape's current outline thickness.
     *
     * @return the shape's current outline thickness
     */
    public float getOutlineThickness() {
        return outlineThickness;
    }

    /**
     * Sets the thickness of the shape's outline.
     *
     * @param thickness the thickness of the shape's outline, or 0 to indicate that no outline should be drawn
     */
    public void setOutlineThickness(float thickness) {
        nativeSetOutlineThickness(thickness);
        updateOrigin(keepCentered);
        this.outlineThickness = thickness;
        boundsNeedUpdate = true;
    }

    /**
     * Returns whether this {@code Shape} is set to be centered.
     *
     * @return {@code true} if this {@code Shape} is set to be centered, {@code false} otherwise.
     */
    public boolean isCentered() {
        return keepCentered;
    }

    /**
     * Sets whether this {@code Shape} has to be centered by keeping its origin at the center
     * of its local bounds. If the origin is changed manually after this shape is set to be
     * centered, it will be set back to the center of the sprite whenever its size changes.
     *
     * @param centered {@code true} to keep this {@code Shape} centered, {@code false} to reset the origin to the point (0,0)
     */
    public void setCentered(boolean centered) {
        updateOrigin(keepCentered = centered);
    }

    /**
     * Gets the shape's current texture.
     *
     * @return the shape's current texture
     */
    @Override
    public ConstTexture getTexture() {
        return texture;
    }

    /**
     * Sets the texture of the shape without affecting the texture rectangle, unless the texture
     * is set to be tileable. If this {@code Sprite} has this texture already applied, this
     * method does nothing. The texture may be {@code null} if no texture is to be used.
     *
     * @param texture the texture of the shape, or {@code null} to indicate that no texture is to be used
     */
    @Override
    public final void setTexture(@Nullable ConstTexture texture) {
        setTexture(texture, false);
    }

    /**
     * Sets the animated texture of the shape without affecting the texture rectangle.
     * The texture may be {@code null} if no animated texture is to be used.
     *
     * @param texture the animated texture of the shape, or {@code null} to indicate that no texture is to be used
     */
    public void setTexture(@Nullable ConstAnimatedTexture texture) {
        setTexture(texture, false);
    }

    /**
     * Sets the texture of the shape. If this {@code Sprite} has this texture already applied,
     * this method does nothing. The texture may be {@code null} if no texture is to be used.
     *
     * @param texture   the texture of the shape, or {@code null} to indicate that no texture is to be used
     * @param resetRect {@code true} to reset the texture rect, {@code false} otherwise
     *                  (this setting is ignored if {@code texture} is tileable)
     */
    @Override
    public void setTexture(@Nullable ConstTexture texture, boolean resetRect) {
        final Pair<Boolean, Boolean> updates = Texture.checkUpdates(texture, this);
        if (updates.value1()) {
            nativeSetTexture((Texture) texture, resetRect && !updates.value2());
            this.texture = texture;
            this.animatedTexture = null;
        }
    }

    /**
     * Sets the animated texture of the shape. The texture may be {@code null} if no texture is to be used.
     *
     * @param texture   the animated texture of the shape, or {@code null} to indicate that no texture is to be used
     * @param resetRect {@code true} to reset the texture rect, {@code false} otherwise
     */
    public void setTexture(@Nullable ConstAnimatedTexture texture, boolean resetRect) {
        if (texture != this.animatedTexture) {
            nativeSetTexture(texture != null ? (Texture) texture.getFrame(0) : null, resetRect);
            this.animatedTexture = texture;
            this.texture = null;
        }
    }

    /**
     * Gets the shape's current animated texture.
     *
     * @return the shape's current animated texture
     */
    @Override
    public ConstAnimatedTexture getAnimatedTexture() {
        return animatedTexture;
    }

    /**
     * Updates this texture for this shape. This method is run by the scene loop and does not need to be invoked manualy.
     *
     * @param elapsedTime the time since the game started
     */
    @Override
    public void updateAnimatedTexture(@NotNull Time elapsedTime) {
        final int frame = (int) (elapsedTime.asSeconds() / animatedTexture.getFrameDuration());
        nativeSetTexture((Texture) animatedTexture.getFrame(frame % animatedTexture.getFramesCount()), false);
    }

    /**
     * Gets the shape's current texture portion.
     *
     * @return the shape's current texture portion
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
        nativeSetTextureRect(IntercomHelper.encodeIntRect(rect));
        this.textureRect = rect;
    }

    protected void updateOrigin(boolean center) {
        if (center) {
            final FloatRect bounds = getLocalBounds();
            setOrigin(bounds.width / 2, bounds.height / 2);
        } else setOrigin(0, 0);
    }

    private void updatePoints() {
        if (pointsNeedUpdate) {
            final int n = nativeGetPointCount();
            final FloatBuffer buffer = ByteBuffer.allocateDirect(2 * n * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            nativeGetPoints(n, buffer);
            points = new Vector2f[n];
            for (int i = 0; i < n; i++) points[i] = new Vector2f(buffer.get(2 * i), buffer.get(2 * i + 1));
            pointsNeedUpdate = false;
        }
    }

    /**
     * Gets the amount of points that defines this shape.
     *
     * @return the amount of points that defines this shape
     */
    public int getPointCount() {
        if (pointsNeedUpdate) updatePoints();
        return points.length;
    }

    /**
     * Gets a point of the shape.
     *
     * @param i the index of the point to retrieve
     * @return the point at the given index
     */
    public Vector2f getPoint(int i) {
        if (pointsNeedUpdate) updatePoints();
        return points[i];
    }

    /**
     * Gets all the points of the shape.
     *
     * @return an array containing the points of the shape
     */
    public Vector2f[] getPoints() {
        final int n = getPointCount();
        Vector2f[] points = new Vector2f[n];
        System.arraycopy(this.points, 0, points, 0, n);
        return points;
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

    /**
     * Sets the rotation, scaling and drawing origin of this shape. If this method is called when this shape
     * is set to be centered, it will be set back to the center of the shape whenever its size changes.
     *
     * @param origin the new origin
     */
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
