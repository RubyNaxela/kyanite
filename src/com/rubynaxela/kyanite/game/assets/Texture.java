package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Sprite;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A wrapper class for JSFML {@link org.jsfml.graphics.Texture} objects, representing a texture asset.
 * The source must be the path to an image file. Supported formats are: BMP, DDS, JPEG, PNG, TGA and PSD.
 */
public class Texture implements Asset {

    private final org.jsfml.graphics.Texture texture;

    Texture(@NotNull org.jsfml.graphics.Texture texture) {
        this.texture = texture;
    }

    /**
     * Creates a new texture from the source specified by the path.
     *
     * @param path path to the source image file
     */
    public Texture(@NotNull Path path) {
        texture = new org.jsfml.graphics.Texture();
        try {
            texture.loadFromFile(path);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Creates a new texture from the source specified by the path.
     *
     * @param path path to the source image file
     */
    public Texture(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new texture from the source image file.
     *
     * @param file the source image file
     */
    public Texture(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new texture from the input stream.
     *
     * @param stream the image data input stream
     */
    public Texture(@NotNull InputStream stream) {
        texture = new org.jsfml.graphics.Texture();
        try {
            texture.loadFromStream(stream);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Applies this texture on the {@link Shape} without affecting the texture rectangle.
     *
     * @param shape the {@link Shape} to apply this texture on
     */
    public void apply(@NotNull Shape shape) {
        shape.setTexture(texture);
    }

    /**
     * Applies this texture on the {@link Sprite} without affecting the texture rectangle.
     *
     * @param sprite the {@link Sprite} to apply this texture on
     */
    public void apply(@NotNull Sprite sprite) {
        sprite.setTexture(texture);
    }

    /**
     * @return whether texture repeating is enabled for this texture
     */
    public boolean isTileable() {
        return texture.isRepeated();
    }

    /**
     * Enables or disables texture repeating. Texture repeating is disabled by default.
     *
     * @param tileable true to enable, false to disable
     */
    public void setTileable(boolean tileable) {
        texture.setRepeated(true);
    }

    /**
     * @return whether texture smooth filter is enabled for this texture
     */
    public boolean isSmooth() {
        return texture.isSmooth();
    }

    /**
     * Enables or disables the smooth filter. The smooth filter is disabled by default.
     *
     * @param smooth true to enable, false to disable
     */
    public void setSmooth(boolean smooth) {
        texture.setSmooth(smooth);
    }
}
