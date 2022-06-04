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

import com.rubynaxela.kyanite.game.assets.Asset;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * This class is designed to create many textures from a single image file. Sample usage:
 * <pre>new TextureAtlas("chocolate_stages.png").get(32, 0, 16, 16)</pre>
 * The above code will create from the specified file a 16px by 16px {@code Texture},
 * of which the top left corner will correspond to the (32,0) point in the image.
 */
public class TextureAtlas implements Asset {

    private static final Map<IntRect, Texture> cache = new HashMap<>();
    private final Image atlas;

    /**
     * Creates a new texture atlas from the source specified by the path.
     *
     * @param path path to the source image file
     * @throws IOException in case an I/O error occurs
     */
    public TextureAtlas(@NotNull Path path) {
        atlas = new Image();
        atlas.loadFromFile(path);
    }

    /**
     * Creates a new texture atlas from the source specified by the path.
     *
     * @param path path to the source image file
     * @throws IOException in case an I/O error occurs
     */
    public TextureAtlas(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new texture atlas from the source image file.
     *
     * @param file the source image file
     * @throws IOException in case an I/O error occurs
     */
    public TextureAtlas(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new texture atlas from the input stream.
     *
     * @param stream the image data input stream
     * @throws IOException in case an I/O error occurs
     */
    public TextureAtlas(@NotNull InputStream stream) {
        atlas = new Image();
        atlas.loadFromStream(stream);
    }

    /**
     * Creates a {@link Texture} containing the image within the bounds of a rectangle of the specified coordinates.
     *
     * @param startX the X-coordinate of the start point
     * @param startY the Y-coordinate of the start point
     * @param endX   the X-coordinate of the end point
     * @param endY   the Y-coordinate of the end point
     * @return a {@link Texture} containing image within the bounds of a rectangle of the specified coordinates
     */
    public Texture get(int startX, int startY, int endX, int endY) {
        final int width = endX - startX, height = endY - startY;
        final IntRect bounds = new IntRect(startX, startY, width, height);
        if (!cache.containsKey(bounds)) {
            if (width < 0) throw new IllegalArgumentException("endX must be greater than startX");
            if (height < 0) throw new IllegalArgumentException("endY must be greater than startY");
            final Texture target = new Texture(atlas, bounds);
            final Texture texture = new Texture(target);
            cache.put(bounds, texture);
            return texture;
        } else return cache.get(bounds);
    }

    /**
     * Creates a {@link Texture} containing the image within the bounds of a rectangle of the specified coordinates.
     *
     * @param start the start point
     * @param end   the end point
     * @return a {@link Texture} containing image within the bounds of a rectangle of the specified coordinates
     */
    public Texture get(@NotNull Vector2i start, @NotNull Vector2i end) {
        return get(start.x, start.y, end.x, end.y);
    }

    /**
     * Creates a {@link Texture} containing the image within the bounds of a
     * rectangle of the specified origin coordinates (the top-left corner) and size.
     *
     * @param x      the X-coordinate of the start point
     * @param y      the Y-coordinate of the start point
     * @param width  the width of the texture
     * @param height the height of the texture
     * @return a {@link Texture} containing image within the bounds of a rectangle of the specified origin and size
     */
    public Texture getRect(int x, int y, int width, int height) {
        return get(x * width, y * height, (x + 1) * width, (y + 1) * height);
    }

    /**
     * Creates a {@link Texture} containing the image within the bounds of a
     * rectangle of the specified origin coordinates (the top-left corner) and size.
     *
     * @param start the start point
     * @param size  the size of the texture
     * @return a {@link Texture} containing image within the bounds of a rectangle of the specified origin and size
     */
    public Texture getRect(@NotNull Vector2i start, @NotNull Vector2i size) {
        return get(start, Vec2.add(start, size));
    }

    /**
     * Creates a {@link Texture} containing the image within the bounds of the specified rectangle
     *
     * @param bounds the texture rectangle bounds
     * @return a {@link Texture} containing image within the bounds of the specified bounds
     */
    public Texture getRect(@NotNull IntRect bounds) {
        return getRect(bounds.left, bounds.top, bounds.width, bounds.height);
    }

    /**
     * Creates a 2D array of textures from this atlas, starting from the specified
     * coordinates of the image file. All textures are the same size. Example usage:<pre>
     * Texture[][] smoke = assets.get&lt;TextureAtlas>("particle.smoke")
     *                           .getMatrix(16, 16, 8, 2);
     * smoke[3][1].apply(this);</pre>
     * The above code will create an 8 columns by 2 rows 2D array of 16x16 textures and
     * apply the fourth texture from the second row to the object that ran this code.
     *
     * @param startX x-coordindate of the texture atlas starting point
     * @param startY y-coordindate of the texture atlas starting point
     * @param width  a single texture width
     * @param height a single texture height
     * @param countX the number of textures in a row
     * @param countY the number of textures in a column
     * @return a 2D array of textures from this atlas
     */
    public Texture[][] getMatrix(int startX, int startY, int width, int height, int countX, int countY) {
        return IntStream.range(0, countX)
                        .mapToObj(x -> IntStream.range(0, countY).mapToObj(y -> getRect(x + startX, y + startY, width, height))
                                                .toArray(Texture[]::new))
                        .toArray(Texture[][]::new);
    }

    /**
     * Creates a 2D array of textures from this atlas, starting from the top-left
     * corner of the image file. All textures are the same size. Example usage:<pre>
     * Texture[][] smoke = assets.get&lt;TextureAtlas>("particle.smoke")
     *                           .getMatrix(16, 16, 8, 2);
     * smoke[3][1].apply(this);</pre>
     * The above code will create an 8 columns by 2 rows 2D array of 16x16 textures and
     * apply the fourth texture from the second row to the object that ran this code.
     *
     * @param width  a single texture width
     * @param height a single texture height
     * @param countX the number of textures in a row
     * @param countY the number of textures in a column
     * @return a 2D array of textures from this atlas
     */
    public Texture[][] getMatrix(int width, int height, int countX, int countY) {
        return getMatrix(0, 0, width, height, countX, countY);
    }

    /**
     * Creates a row of textures from this atlas, starting from the specified
     * coordinates of the image file. All textures are the same size. Example usage:<pre>
     * Texture[] smoke = assets.get&lt;TextureAtlas>("particle.smoke")
     *                         .getRow(16, 16, 8);
     * smoke[3].apply(this);</pre>
     * The above code will create an array of 8 16x16 textures and apply the fourth one to the object that ran this code.
     *
     * @param width  a single texture width
     * @param height a single texture height
     * @param count  the number of textures
     * @return an array of textures from this atlas
     */
    public Texture[] getRow(int startX, int startY, int width, int height, int count) {
        return IntStream.range(0, count).mapToObj(x -> getRect(startX + x, startY, width, height)).toArray(Texture[]::new);
    }

    /**
     * Creates a row of textures from this atlas, starting from the top-left
     * corner of the image file. All textures are the same size. Example usage:<pre>
     * Texture[] smoke = assets.get&lt;TextureAtlas>("particle.smoke")
     *                         .getRow(16, 16, 8);
     * smoke[3].apply(this);</pre>
     * The above code will create an array of 8 16x16 textures and apply the fourth one to the object that ran this code.
     *
     * @param width  a single texture width
     * @param height a single texture height
     * @param count  the number of textures
     * @return an array of textures from this atlas
     */
    public Texture[] getRow(int width, int height, int count) {
        return getRow(0, 0, width, height, count);
    }

    /**
     * Creates a column of textures from this atlas, starting from the specified
     * coordinates of the image file. All textures are the same size. Example usage:<pre>
     * Texture[] smoke = assets.get&lt;TextureAtlas>("particle.smoke")
     *                         .getRow(16, 16, 8);
     * smoke[3].apply(this);</pre>
     * The above code will create an array of 8 16x16 textures and apply the fourth one to the object that ran this code.
     *
     * @param width  a single texture width
     * @param height a single texture height
     * @param count  the number of textures
     * @return an array of textures from this atlas
     */
    public Texture[] getColumn(int startX, int startY, int width, int height, int count) {
        return IntStream.range(0, count).mapToObj(y -> getRect(startX, startY + y, width, height)).toArray(Texture[]::new);
    }

    /**
     * Creates a column of textures from this atlas, starting from the top-left
     * corner of the image file. All textures are the same size. Example usage:<pre>
     * Texture[] smoke = assets.get&lt;TextureAtlas>("particle.smoke")
     *                         .getRow(16, 16, 8);
     * smoke[3].apply(this);</pre>
     * The above code will create an array of 8 16x16 textures and apply the fourth one to the object that ran this code.
     *
     * @param width  a single texture width
     * @param height a single texture height
     * @param count  the number of textures
     * @return an array of textures from this atlas
     */
    public Texture[] getColumn(int width, int height, int count) {
        return getColumn(0, 0, width, height, count);
    }
}
