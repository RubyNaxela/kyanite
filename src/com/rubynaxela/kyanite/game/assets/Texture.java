package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Sprite;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A wrapper class for JSFML {@link org.jsfml.graphics.Texture} objects, representing a texture asset.
 * The source must be the path or the stream to an image file. Supported formats are: BMP, DDS, JPEG,
 * PNG, TGA and PSD. If the specified image data source is not valid (for instance, if the specified
 * file does not exist), the texture is replaced with a magenta-black checkboard.
 */
public class Texture implements Asset {

    private final org.jsfml.graphics.Texture texture;
    private boolean missing = false;

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
            e.printStackTrace();
            missingTexture();
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
            e.printStackTrace();
            missingTexture();
        }
    }

    /**
     * Applies this texture on the {@link Shape}. Does not affect the texture rectangle, unless the texture is missing.
     *
     * @param shape the {@link Shape} to apply this texture on
     */
    public void apply(@NotNull Shape shape) {
        shape.setTexture(texture);
        if (missing) {
            final FloatRect bounds = shape.getLocalBounds();
            shape.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
        }
    }

    /**
     * Applies this texture on the {@link Sprite}. Does not affect the texture rectangle, unless the texture is missing.
     *
     * @param sprite the {@link Sprite} to apply this texture on
     */
    public void apply(@NotNull Sprite sprite) {
        sprite.setTexture(texture);
        if (missing) {
            final FloatRect bounds = sprite.getLocalBounds();
            sprite.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
        }
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
        texture.setRepeated(tileable);
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

    private void missingTexture() {
        try {
            missing = true;
            texture.loadFromStream(Objects.requireNonNull(getClass().getResourceAsStream("/res/missing_texture.png")));
            texture.setRepeated(true);
        } catch (java.io.IOException ex) {
            throw new IOException(ex);
        }
    }
}
