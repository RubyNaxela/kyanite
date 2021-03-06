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
import com.rubynaxela.kyanite.core.StreamUtil;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serial;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.file.Path;

/**
 * Provides methods for loading, manipulating and saving images.
 */
@SuppressWarnings("deprecation")
public class Image extends org.jsfml.graphics.Image {

    private Vector2i size = Vector2i.zero();
    private IntBuffer pixels = null;
    private boolean changed = true;

    /**
     * Constructs a new empty image.
     */
    public Image() {
        super();
    }

    Image(long wrap) {
        super(wrap);
        sync();
    }

    private static int srcBlend(int s, int d) {
        final int sa = (s >> 24) & 0xFF, sb = (s >> 16) & 0xFF, sg = (s >> 8) & 0xFF, sr = s & 0xFF,
                da = (d >> 24) & 0xFF, db = (d >> 16) & 0xFF, dg = (d >> 8) & 0xFF, dr = d & 0xFF,
                r = (sr * sa + dr * (255 - sa)) / 255, g = (sg * sa + dg * (255 - sa)) / 255,
                b = (sb * sa + db * (255 - sa)) / 255, a = (sa + da * (255 - sa)) / 255;
        return (a << 24) | (b << 16) | (g << 8) | r;
    }

    /**
     * Swaps the red and blue channel where pixels are in the ARGB format but ABGR is required.
     *
     * @param color an encoded color
     * @return the specified color with the red and blue channels swapped
     */
    private static int swapRB(int color) {
        return (color & 0xFF00FF00) | ((color & 0xFF) << 16) | ((color >> 16) & 0xFF);
    }

    /**
     * Generates a new image and fills it with a color.
     *
     * @param width  the image's width
     * @param height the image's height
     * @param color  the fill color of the image
     */
    public void create(int width, int height, @NotNull Color color) {
        if (width < 0 || height < 0) throw new IllegalArgumentException("width: " + width + ", height: " + height);
        size = new Vector2i(width, height);
        if (width > 0 && height > 0)
            pixels = ByteBuffer.allocateDirect(4 * width * height).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        else pixels = null;
        if (!color.equals(Colors.BLACK)) fill(color);
    }

    /**
     * Generates a new image and fills it with black.
     *
     * @param width  the image's width
     * @param height the image's height
     */
    public final void create(int width, int height) {
        create(width, height, Colors.BLACK);
    }

    /**
     * Creates a new image by converting an AWT {@link BufferedImage}.
     *
     * @param image the AWT buffered image to convert
     */
    public void create(BufferedImage image) {
        final int w = image.getWidth();
        final int h = image.getHeight();
        final int[] data = new int[w * h];
        create(w, h, image.getRGB(0, 0, w, h, data, 0, w));
    }

    /**
     * Creates a new image from the given pixel data. The pixel data is
     * expected to be in the {@link BufferedImage#TYPE_INT_ARGB} color format.
     *
     * @param width  the image's width
     * @param height the image's height
     * @param pixels the image's pixel data in 32-bit ARGB format
     */
    public void create(int width, int height, int[] pixels) {
        if (pixels.length != width * height)
            throw new IllegalArgumentException("Pixel buffer size does not fit the specified dimensions");
        create(width, height);
        if (width > 0 && height > 0) {
            for (int i = 0; i < pixels.length; i++) pixels[i] = swapRB(pixels[i]);
            this.pixels.rewind();
            this.pixels.put(pixels);
        }
    }

    /**
     * Fully loads all available bytes from an {@link java.io.InputStream} and attempts to load the image from it.
     *
     * @param in the input stream to read from
     * @throws IOException in case an I/O error occurs
     */
    public void loadFromStream(InputStream in) throws IOException {
        SFMLErrorCapture.start();
        final boolean success = nativeLoadFromMemory(StreamUtil.readStream(in));
        final String err = SFMLErrorCapture.finish();
        if (!success) throw new IOException(err);
        sync();
    }

    /**
     * Attempts to load an image from a file.
     *
     * @param path the file to load the texture from
     * @throws IOException in case an I/O error occurs
     */
    public void loadFromFile(Path path) throws IOException {
        SFMLErrorCapture.start();
        final boolean success = nativeLoadFromMemory(StreamUtil.readFile(path));
        final String err = SFMLErrorCapture.finish();
        if (!success) throw new IOException(err);
        sync();
    }

    /**
     * Attempts to save the image to a file.
     *
     * @param path the path to the file to write
     * @throws IOException in case an I/O error occurs
     */
    public void saveToFile(Path path) throws IOException {
        commit();
        SFMLErrorCapture.start();
        final boolean success = nativeSaveToFile(path.toAbsolutePath().toString());
        final String err = SFMLErrorCapture.finish();
        if (!success) throw new IOException(err);
    }

    final void sync() {
        size = IntercomHelper.decodeVector2i(nativeGetSize());
        create(size.x, size.y);
        if (size.x > 0 && size.y > 0) nativeSync(size.x, size.y, pixels);
        changed = false;
    }

    final void commit() {
        if (changed) {
            nativeCommit(size.x, size.y, pixels);
            changed = false;
        }
    }

    private int pixel(int x, int y) {
        return y * size.x + x;
    }

    /**
     * Gets the size of the image.
     *
     * @return the size of the image
     */
    public Vector2i getSize() {
        return size;
    }

    private void fill(@NotNull Color color) {
        final int c = IntercomHelper.encodeColor(color);
        for (int i = 0; i < size.x * size.y; i++) pixels.put(i, c);
    }

    /**
     * Creates a transparency mask from the given color.
     *
     * @param color the color to be made transparent
     * @param alpha the alpha value to assign to pixels matching the color key
     */
    public void createMaskFromColor(Color color, int alpha) {
        final int c = IntercomHelper.encodeColor(color), a = alpha << 24;
        for (int i = 0; i < size.x * size.y; i++) if (pixels.get(i) == c) pixels.put(i, a | (pixels.get(i) & 0xFFFFFF));
        changed = true;
    }

    /**
     * Creates a transparency mask from the given color, making matching pixels fully transparent.
     *
     * @param color the color to be made transparent
     */
    public final void createMaskFromColor(Color color) {
        createMaskFromColor(color, 0);
    }

    /**
     * Copies a portion of another image onto this image.
     *
     * @param source     the source image
     * @param destX      the destination X coordinate
     * @param destY      the destination X coordinate
     * @param sourceRect the source rectangle (an empty rectangle means the full image)
     * @param applyAlpha {@code true} to copy alpha values as well, {@code false} to leave the destination alpha
     */
    public void copy(@NotNull Image source, int destX, int destY, @NotNull IntRect sourceRect, boolean applyAlpha) {

        if (size.x == 0 || size.y == 0 || source.size.x == 0 || source.size.y == 0) return;

        int left, top, width, height;
        if (sourceRect.width == 0 || sourceRect.height == 0) {
            left = 0;
            top = 0;
            width = source.size.x;
            height = source.size.y;
        } else {
            left = Math.max(0, sourceRect.top);
            top = Math.max(0, sourceRect.top);
            width = Math.min(sourceRect.width, source.size.x - left);
            height = Math.min(sourceRect.height, source.size.y - top);
        }

        if (destX + width > size.x) width = size.x - destX;
        if (destY + height > size.y) height = size.y - destY;

        if (width <= 0 || height <= 0) return;

        if (applyAlpha) {
            for (int y = 0; y < height; y++) {
                int src = source.pixel(left, top + y);
                int dst = pixel(destX, destY + y);
                for (int x = 0; x < width; x++) {
                    pixels.put(dst, srcBlend(source.pixels.get(src), pixels.get(dst)));
                    src++;
                    dst++;
                }
            }
        } else {
            final int[] buffer = new int[width];
            for (int y = 0; y < height; y++) {
                source.pixels.position(source.pixel(left, top + y));
                source.pixels.get(buffer, 0, width);
                pixels.position(pixel(destX, destY + y));
                pixels.put(buffer, 0, width);
            }
        }

        changed = true;
    }

    /**
     * Copies a portion of another image onto this image.
     *
     * @param source     the source image
     * @param destX      the destination X coordinate
     * @param destY      the destination X coordinate
     * @param sourceRect the source rectangle. An empty rectangle means the full image
     */
    public final void copy(@NotNull Image source, int destX, int destY, @NotNull IntRect sourceRect) {
        copy(source, destX, destY, sourceRect, false);
    }

    /**
     * Copies another image onto this image.
     *
     * @param source the source image
     * @param destX  the destination X coordinate
     * @param destY  the destination X coordinate
     */
    public final void copy(@NotNull Image source, int destX, int destY) {
        copy(source, destX, destY, IntRect.EMPTY, false);
    }

    /**
     * Sets the color of a certain pixel.
     *
     * @param x     the pixel's X coordinate
     * @param y     the pixel's Y coordinate
     * @param color the color to apply to the pixel
     */
    public void setPixel(int x, int y, Color color) {
        if (x < 0 || y < 0 || x >= size.x || y >= size.y) throw new PixelOutOfBoundsException(x, y);
        pixels.put(y * size.x + x, IntercomHelper.encodeColor(color));
        changed = true;
    }

    /**
     * Gets the color of a certain pixel.
     *
     * @param x the pixel's X coordinate
     * @param y the pixel's Y coordinate
     * @return the pixel's color
     */
    public Color getPixel(int x, int y) {
        if (x < 0 || y < 0 || x >= size.x || y >= size.y) throw new PixelOutOfBoundsException(x, y);
        return IntercomHelper.decodeColor(pixels.get(y * size.x + x));
    }

    /**
     * Retrieves a copy of all the pixels of the image in 32-bit ARGB color format. The retrieved
     * image data is compatible to the {@link BufferedImage#TYPE_INT_ARGB} color format.
     *
     * @return a copy of all the pixels of the image in 32-bit ARGB color format
     */
    public int[] getPixels() {
        final int[] data = new int[pixels.capacity()];
        for (int i = 0; i < data.length; i++) data[i] = swapRB(pixels.get(i));
        return data;
    }

    /**
     * Converts this image to an AWT {@link BufferedImage}. The resulting buffered
     * image will be of {@link BufferedImage#TYPE_INT_ARGB} color format.
     *
     * @return the resulting buffered image
     */
    public BufferedImage toBufferedImage() {
        final BufferedImage bufferedImage = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
        if (size.x > 0 && size.y > 0) bufferedImage.setRGB(0, 0, size.x, size.y, getPixels(), 0, size.x);
        return bufferedImage;
    }

    /**
     * Flips the image horizontally.
     */
    public void flipHorizontally() {
        for (int y = 0; y < size.y; y++) {
            for (int x = 0; x < size.x / 2; x++) {
                final int i1 = y * size.x + x, i2 = y * size.x + size.x - 1 - x;
                if (i1 != i2) {
                    final int temp = pixels.get(i1);
                    pixels.put(i1, pixels.get(i2));
                    pixels.put(i2, temp);
                }
            }
        }
        changed = true;
    }

    /**
     * Flips the image vertically.
     */
    public void flipVertically() {
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y / 2; y++) {
                final int i1 = y * size.x + x, i2 = (size.y - 1 - y) * size.x + x;
                if (i1 != i2) {
                    final int temp = pixels.get(i1);
                    pixels.put(i1, pixels.get(i2));
                    pixels.put(i2, temp);
                }
            }
        }
        changed = true;
    }

    /**
     * Thrown if a non-existing pixel is being accessed by either {@link #getPixel} or {@link #setPixel}.
     */
    public static class PixelOutOfBoundsException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -7306446590734505000L;

        /**
         * Constructs a new exception.
         *
         * @param x the X coordinate of the pixel that was being accessed
         * @param y the Y coordinate of the pixel that was being accessed
         */
        public PixelOutOfBoundsException(int x, int y) {
            super("(" + x + ", " + y + ")");
        }
    }
}
