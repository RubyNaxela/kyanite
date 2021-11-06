package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.TextureCreationException;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is designed to load many textures stored in a single image file
 */
public class TextureAtlas implements Asset {

    private static final Map<IntRect, Texture> cache = new HashMap<>();
    private final Image atlas;

    /**
     * Creates a new texture atlas from the source specified by the path.
     *
     * @param path path to the source image file
     */
    public TextureAtlas(@NotNull Path path) {
        atlas = new Image();
        try {
            atlas.loadFromFile(path);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Creates a new texture atlas from the source specified by the path.
     *
     * @param path path to the source image file
     */
    public TextureAtlas(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new texture atlas from the source image file.
     *
     * @param file the source image file
     */
    public TextureAtlas(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new texture atlas from the input stream.
     *
     * @param stream the image data input stream
     */
    public TextureAtlas(@NotNull InputStream stream) {
        atlas = new Image();
        try {
            atlas.loadFromStream(stream);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
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
            final org.jsfml.graphics.Texture target = new org.jsfml.graphics.Texture();
            try {
                target.loadFromImage(atlas, bounds);
            } catch (TextureCreationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            final Texture texture = new Texture(target);
            cache.put(bounds, texture);
            return texture;
        } else return cache.get(bounds);
    }

    /**
     * Creates a {@link RectangleShape} textured with the image within the bounds of a rectangle of the specified coordinates.
     *
     * @param startX         the X-coordinate of the start point
     * @param startY         the Y-coordinate of the start point
     * @param endX           the X-coordinate of the end point
     * @param endY           the Y-coordinate of the end point
     * @param originAtCenter whether or not the origin of the result shape has
     *                       to be in its center; the default value is {@code false}
     * @return a {@link RectangleShape} textured with the image within the bounds of a rectangle of the specified coordinates
     */
    public RectangleShape getRectangleShape(int startX, int startY, int endX, int endY, boolean originAtCenter) {
        final float width = endX - startX, height = endY - startY;
        final RectangleShape rectangle = new RectangleShape(Vec2.f(width, height));
        final Texture texture = get(startX, startY, endX, endY);
        texture.apply(rectangle);
        if (originAtCenter) rectangle.setOrigin(width / 2, height / 2);
        return rectangle;
    }

    /**
     * Creates a {@link RectangleShape} textured with the image within the bounds of a rectangle of the specified coordinates.
     *
     * @param startX the X-coordinate of the start point
     * @param startY the Y-coordinate of the start point
     * @param endX   the X-coordinate of the end point
     * @param endY   the Y-coordinate of the end point
     * @return a {@link RectangleShape} textured with the image within the bounds of a rectangle of the specified coordinates
     */
    public RectangleShape getRectangleShape(int startX, int startY, int endX, int endY) {
        return getRectangleShape(startX, startY, endX, endY, false);
    }
}
