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
import com.rubynaxela.kyanite.core.UnsafeOperations;
import com.rubynaxela.kyanite.data.Pair;
import com.rubynaxela.kyanite.game.assets.Asset;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.system.IOException;
import com.rubynaxela.kyanite.window.BasicWindow;
import com.rubynaxela.kyanite.window.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents a 2D texture stored on the graphics card for rendering. The source must be the path to
 * an image file or an {@link InputStream} to image data. Supported formats are: BMP, DDS, PNG, TGA
 * and PSD. If the specified image data source is not valid (for instance, if the data is corrupt,
 * or the specified file does not exist), the texture is replaced with a magenta-black checkboard.
 */
@SuppressWarnings("deprecation")
public class Texture extends org.jsfml.graphics.Texture implements ConstTexture, Asset {

    private static final int MAXIMUM_SIZE;

    static {
        SFMLNative.loadNativeLibraries();
        MAXIMUM_SIZE = nativeGetMaximumSize();
    }

    private final String path;
    private boolean smooth = false, repeated = false, missing = false, suppressWarning = false;
    private Vector2i size = Vector2i.zero();

    /**
     * Creates a new texture from the source specified by the path.
     *
     * @param path path to the source image file
     */
    public Texture(@NotNull Path path) {
        this(path, IntRect.EMPTY);
    }

    /**
     * Creates a new texture from the source specified by the path, cropping it according to the specified rectangle.
     *
     * @param path path to the source image file
     * @param area the area to be taken from the image as the texture
     */
    public Texture(@NotNull Path path, @NotNull IntRect area) {
        this.path = path.toString();
        try {
            loadFromFile(path, area);
        } catch (IOException e) {
            if (!suppressWarning) e.printStackTrace();
            missingTexture();
        }
    }

    /**
     * Creates a new texture from the source specified by the path.
     *
     * @param path path to the source image file
     */
    public Texture(@NotNull String path) {
        this(Paths.get(path), IntRect.EMPTY);
    }

    /**
     * Creates a new texture from the source specified by the path, cropping it according to the specified rectangle.
     *
     * @param path path to the source image file
     * @param area the area to be taken from the image as the texture
     */
    public Texture(@NotNull String path, @NotNull IntRect area) {
        this(Paths.get(path), area);
    }

    /**
     * Creates a new texture from the source image file.
     *
     * @param file the source image file
     */
    public Texture(@NotNull File file) {
        this(file.toPath(), IntRect.EMPTY);
    }

    /**
     * Creates a new texture from a {@link File}, cropping it according to the specified rectangle.
     *
     * @param file the source image file
     * @param area the area to be taken from the image as the texture
     */
    public Texture(@NotNull File file, @NotNull IntRect area) {
        this(file.toPath(), area);
    }

    /**
     * Creates a new texture from an {@link InputStream}.
     *
     * @param stream the image data input stream
     */
    public Texture(@NotNull InputStream stream) {
        this(stream, IntRect.EMPTY);
    }

    /**
     * Creates a new texture from an {@link InputStream}, cropping it according to the specified rectangle.
     *
     * @param stream the image data input stream
     * @param area   the area to be taken from the image as the texture
     */
    public Texture(@NotNull InputStream stream, @NotNull IntRect area) {
        this.path = this + " (created from an InputStream)";
        try {
            loadFromStream(stream, area);
        } catch (IOException e) {
            if (!suppressWarning) e.printStackTrace();
            missingTexture();
        }
    }

    /**
     * Creates a new texture from an {@link Image}.
     *
     * @param image the image data
     */
    public Texture(@NotNull Image image) {
        this(image, IntRect.EMPTY);
    }

    /**
     * Creates a new texture from an {@link Image}, cropping it according to the specified rectangle.
     *
     * @param image the image data
     * @param area  the area to be taken from the image as the texture
     */
    public Texture(@NotNull Image image, @NotNull IntRect area) {
        this.path = this + " (created from an InputStream)";
        try {
            loadFromImage(image, area);
        } catch (IOException e) {
            if (!suppressWarning) e.printStackTrace();
            missingTexture();
        }
    }

    /**
     * Constructs a new texture by copying another texture.
     *
     * @param other the texture to copy
     */
    public Texture(ConstTexture other) {
        this(other, false);
    }

    Texture(long wrap) {
        super(wrap);
        this.path = this + " (created with a private constructor)";
        updateSize();
        smooth = nativeIsSmooth();
        repeated = nativeIsRepeated();
    }

    private Texture(ConstTexture other, boolean suppressWarning) {
        super(((Texture) other).nativeCopy());
        this.path = this + " (created with the copy constructor)";
        this.suppressWarning = suppressWarning;
        UnsafeOperations.manageSFMLObject(this, true);
        updateSize();
        smooth = nativeIsSmooth();
        repeated = nativeIsRepeated();
    }

    private Texture() {
        this.path = this + " (created with the default constructor)";
    }

    /**
     * Generates an empty texture with the specified dimensions.
     *
     * @param width  the texture's width
     * @param height the texture's height
     * @throws TextureCreationException if the texture could not be created
     */
    public Texture(int width, int height) {
        this.path = this + " (created with the Texture(int,int) constructor)";
        if (!nativeCreate(width, height)) throw new TextureCreationException("Failed to create texture.");
        updateSize();
    }

    /**
     * Creates a tileable magenta-black checkboard texture, typically used to indicate a texture loading error.
     *
     * @return a tileable magenta-black checkboard texture
     */
    public static Texture missing() {
        final Texture texture = new Texture();
        texture.missingTexture();
        return texture;
    }

    /**
     * Gets the maximum texture size supported by the current hardware.
     *
     * @return the maximum texture size supported by the current hardware
     */
    public static int getMaximumSize() {
        return MAXIMUM_SIZE;
    }

    /**
     * Activates a texture for rendering with the specified coordinate type. This
     * is required only if you wish to use Kyanite textures in custom OpenGL code.
     *
     * @param texture        the texture to bind, or {@code null} to indicate that no texture is to be used
     * @param coordinateType the coordinate type to use
     */
    public static void bind(@Nullable ConstTexture texture, @NotNull CoordinateType coordinateType) {
        nativeBind((Texture) texture, coordinateType.ordinal());
    }

    /**
     * Activates a texture for rendering, using the {@link Texture.CoordinateType#NORMALIZED} coordinate type.
     *
     * @param texture the texture to bind, or {@code null} to indicate that no texture is to be used
     */
    public static void bind(@Nullable ConstTexture texture) {
        bind(texture, CoordinateType.NORMALIZED);
    }

    /**
     * @return a {@link Pair} of two {@code boolean}s: the first one indicating if the texture needs
     * to be updated; the second one indicating if the texture rectangle was affected by this method
     */
    static Pair<Boolean, Boolean> checkUpdates(@Nullable ConstTexture constTexture, @NotNull SceneObject sceneObject) {
        if (sceneObject.getTexture() == constTexture) return new Pair<>(false, false);
        final Texture texture = (Texture) constTexture;
        final FloatRect bounds = sceneObject.getGlobalBounds();
        boolean textureRectAffected = false;
        if (texture != null) {
            if (texture.isTileable()) {
                texture.checkBounds(bounds);
                sceneObject.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
                textureRectAffected = true;
            }
            if (texture.missing) {
                sceneObject.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
                if (!texture.suppressWarning) new IOException("Missing texture: " + texture.path).printStackTrace();
                textureRectAffected = true;
            }
        }
        return new Pair<>(true, textureRectAffected);
    }

    private void loadFromStream(@NotNull InputStream in, @NotNull IntRect area) throws IOException {
        try {
            final Image image = new Image();
            image.loadFromStream(in);
            loadFromImage(image, area);
        } catch (TextureCreationException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void loadFromStream(@NotNull InputStream in) throws IOException {
        loadFromStream(in, IntRect.EMPTY);
    }

    private void loadFromFile(@NotNull Path path, @NotNull IntRect area) throws IOException {
        Context.getContext();
        SFMLErrorCapture.start();
        final boolean success = nativeLoadFromFile(path.toAbsolutePath().toString(), IntercomHelper.encodeIntRect(area));
        final String msg = SFMLErrorCapture.finish();
        if (!success) throw new IOException(msg);
        updateSize();
    }

    private void loadFromImage(@NotNull Image image, @NotNull IntRect area) throws TextureCreationException {
        image.commit();
        Context.getContext();
        if (!nativeLoadFromImage(image, IntercomHelper.encodeIntRect(area)))
            throw new TextureCreationException("Failed to load texture from image.");
        updateSize();
    }

    void checkBounds(@NotNull FloatRect bounds) {
        if (isTileable() && (bounds.width == 0 || bounds.height == 0))
            throw new ArithmeticException("Indeterminate number of texture repetitions for shape size: [width=" +
                                          bounds.width + ";height=" + bounds.height + "]. Please set a size that " +
                                          "is non-zero on both axes before applying a tileable texture.");
    }

    private void updateSize() {
        size = IntercomHelper.decodeVector2i(nativeGetSize());
    }

    /**
     * Gets the dimensions of the texture.
     *
     * @return the dimensions of the texture.
     */
    public Vector2i getSize() {
        return size;
    }

    @Override
    public Image copyToImage() {
        final Image image = new Image(nativeCopyToImage());
        UnsafeOperations.manageSFMLObject(image, true);
        return image;
    }

    @Override
    public boolean isSmooth() {
        return smooth;
    }

    /**
     * Enables or disables the smooth filter. The smooth filter is disabled by default.
     *
     * @param smooth {@code true} to enable, {@code false} to disable
     */
    public void setSmooth(boolean smooth) {
        nativeSetSmooth(smooth);
        this.smooth = smooth;
    }

    @Override
    public boolean isTileable() {
        return repeated;
    }

    /**
     * Enables or disables texture repeating. Texture repeating is disabled by default.
     *
     * @param repeated {@code true} to enable, {@code false} to disable.
     */
    public void setTileable(boolean repeated) {
        nativeSetRepeated(repeated);
        this.repeated = repeated;
    }

    /**
     * Updates a part of the texture from an image.
     *
     * @param image the image to update from
     * @param x     the X offset inside the texture
     * @param y     the Y offset inside the texture
     */
    public void update(@NotNull Image image, int x, int y) {
        image.commit();
        nativeUpdate(image, x, y);
    }

    /**
     * Updates a part of the texture from the contents of a window.
     *
     * @param window the window to update from
     * @param x      the X offset inside the texture
     * @param y      the Y offset inside the texture
     */
    public void update(@NotNull BasicWindow window, int x, int y) {
        nativeUpdate(Objects.requireNonNull(window), x, y);
    }

    /**
     * Updates the texture from the contents of a window.
     *
     * @param window the window to update from
     */
    public final void update(@NotNull BasicWindow window) {
        update(window, 0, 0);
    }

    private void missingTexture() {
        missing = true;
        loadFromStream(Objects.requireNonNull(getClass().getResourceAsStream("/res/missing_texture.png")));
        setTileable(true);
    }

    /**
     * Creates a {@link RectangleShape} with this texture applied. The
     * size of the {@code RectangleShape} matches this texture's size.
     *
     * @param centered whether the {@code RectangleShape} has to be kept centered
     * @return a {@code RectangleShape} with this texture
     */
    public RectangleShape createRectangleShape(boolean centered) {
        final RectangleShape rectangle = new RectangleShape(Vec2.f(getSize()), centered);
        rectangle.setTexture(this);
        return rectangle;
    }

    /**
     * Creates a {@link RectangleShape} with this texture applied. The
     * size of the {@code RectangleShape} matches this texture's size.
     *
     * @return a {@code RectangleShape} with this texture
     */
    public RectangleShape createRectangleShape() {
        return createRectangleShape(false);
    }

    /**
     * Enumeation of texture coordinate types that can be used for rendering.
     */
    public enum CoordinateType {

        /**
         * Normalized OpenGL coordinates, ranging from 0 to 1.
         */
        NORMALIZED,
        /**
         * Pixel coordinates, ranging from 0 to the respective dimension (width or height).
         */
        PIXELS
    }
}
