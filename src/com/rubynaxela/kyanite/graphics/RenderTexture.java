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
import com.rubynaxela.kyanite.core.SFMLErrorCapture;
import com.rubynaxela.kyanite.core.SFMLNative;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a render target for off-screen 2D rendering into a texture.
 */
@SuppressWarnings("deprecation")
public class RenderTexture extends org.jsfml.graphics.RenderTexture implements RenderTarget {

    private final Texture texture;
    private Vector2i size = Vector2i.zero();
    private ConstView defaultView, view;

    /**
     * Constructs a new render texture.
     */
    public RenderTexture() {
        super();
        SFMLNative.ensureDisplay();
        texture = new Texture(nativeGetTexture());
    }

    /**
     * Sets up the render texture.
     *
     * @param width       the texture's width
     * @param height      the texture's height
     * @param depthBuffer {@code true} to generate a depth buffer, {@code false} otherwise
     *                    (use this only in case you wish to do 3D rendering to this texture)
     * @throws TextureCreationException if the render texture could not be created
     */
    public void create(int width, int height, boolean depthBuffer) throws TextureCreationException {
        size = Vector2i.zero();
        SFMLErrorCapture.start();
        final boolean success = nativeCreateTexture(width, height, depthBuffer);
        final String msg = SFMLErrorCapture.finish();
        if (!success) throw new TextureCreationException(msg);
        defaultView = new View(nativeGetDefaultView());
        if (view == null) view = defaultView;
        size = new Vector2i(width, height);
    }

    /**
     * Sets up the render texture without a depth buffer.
     *
     * @param width  the texture's width
     * @param height the texture's height
     * @throws TextureCreationException if the render texture could not be created
     */
    public final void create(int width, int height) throws TextureCreationException {
        create(width, height, false);
    }

    /**
     * Checks whether texture smoothing is enabled.
     *
     * @return {@code true} if enabled, {@code false} if not
     */
    public boolean isSmooth() {
        return texture.isSmooth();
    }

    /**
     * Enables or disables textures smoothing.
     *
     * @param smooth {@code true} to enable, {@code false} to disable
     */
    public void setSmooth(boolean smooth) {
        texture.setSmooth(smooth);
    }

    /**
     * Checks whether texture repeating is enabled for the underlying texture.
     *
     * @return {@code true} if enabled, {@code false} if disabled
     */
    public boolean isRepeated() {
        return texture.isTileable();
    }

    /**
     * Enables or disables texture repeating for the underlying texture. Texture repeating is disabled by default.
     *
     * @param repeated {@code true} to enable, {@code false} to disable
     */
    public void setRepeated(boolean repeated) {
        texture.setTileable(repeated);
    }

    /**
     * Activates or deactivates the render texture for rendering.
     *
     * @param active {@code true} to activate, {@code false} to deactivate
     */
    public native void setActive(boolean active);

    /**
     * Gets the target texture.
     *
     * @return the target texture.
     */
    public ConstTexture getTexture() {
        return texture;
    }

    @Override
    public void clear(@NotNull Color color) {
        nativeClear(IntercomHelper.encodeColor(color));
    }

    @Override
    public ConstView getView() {
        return view;
    }

    @Override
    public void setView(@NotNull ConstView view) {
        this.view = view;
        nativeSetView((View) view);
    }

    @Override
    public ConstView getDefaultView() {
        return defaultView;
    }

    @Override
    public final Vector2f mapPixelToCoords(@NotNull Vector2i point) {
        return mapPixelToCoords(point, view);
    }

    @Override
    public Vector2f mapPixelToCoords(@NotNull Vector2i point, @NotNull ConstView view) {
        return IntercomHelper.decodeVector2f(nativeMapPixelToCoords(IntercomHelper.encodeVector2i(point), view));
    }

    @Override
    public final Vector2i mapCoordsToPixel(@NotNull Vector2f point) {
        return mapCoordsToPixel(point, view);
    }

    @Override
    public Vector2i mapCoordsToPixel(@NotNull Vector2f point, @NotNull ConstView view) {
        return IntercomHelper.decodeVector2i(nativeMapCoordsToPixel(IntercomHelper.encodeVector2f(point), view));
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    /**
     * Clears the target with black.
     */
    public void clear() {
        nativeClear(0xFF000000);
    }

    @Override
    public void pushGLStates() {
        super.pushGLStates();
    }

    @Override
    public void popGLStates() {
        super.popGLStates();
    }

    @Override
    public void resetGLStates() {
        super.resetGLStates();
    }
}
