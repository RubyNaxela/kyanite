package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A wrapper class for JSFML {@link org.jsfml.graphics.Texture} objects, representing a texture asset.
 * The source must be the path or an {@link InputStream}to an image file. Supported formats are: BMP,
 * DDS, JPEG, PNG, TGA and PSD. If the specified image data source is not valid (for instance, if
 * if the specified file does not exist), the texture is replaced with a magenta-black checkboard.
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
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Texture(@NotNull Path path) {
        texture = new org.jsfml.graphics.Texture();
        try {
            final File imageFile = path.toFile();
            if (ImageIO.getImageReaders(ImageIO.createImageInputStream(imageFile)).next().getFormatName().equals("JPEG")) {
                final File tmpImageFile = new File(imageFile.getParentFile(), "." + imageFile.getName() + ".png");
                final BufferedImage bufferedImage = ImageIO.read(imageFile);
                ImageIO.write(bufferedImage, "png", tmpImageFile);
                texture.loadFromFile(tmpImageFile.toPath());
                tmpImageFile.delete();
            } else texture.loadFromFile(path);
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
    @Contract(pure = true)
    public boolean isTileable() {
        return texture.isRepeated();
    }

    /**
     * Enables or disables texture repeating. Texture repeating is disabled by default. Note that this option will be applied
     * only
     *
     * @param tileable true to enable, false to disable
     */
    public void setTileable(boolean tileable) {
        texture.setRepeated(tileable);
    }

    /**
     * @return whether texture smooth filter is enabled for this texture
     */
    @Contract(pure = true)
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

    /**
     * Gets the dimensions of this texture.
     *
     * @return the dimensions of this texture
     */
    @Contract(pure = true)
    @NotNull
    public Vector2i getSize() {
        return texture.getSize();
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

    /**
     * Creates a {@link RectangleShape} with this texture applied. The
     * size of the {@code RectangleShape} matches this texture's size.
     *
     * @param originAtCenter whether or not the origin of the {@code RectangleShape} has
     *                       to be in its center; the default value is {@code false}
     * @return a {@link RectangleShape} representing this texture
     */
    public RectangleShape createRectangleShape(boolean originAtCenter) {
        final RectangleShape rectangle = new RectangleShape(Vec2.f(getSize()));
        apply(rectangle);
        if (originAtCenter) rectangle.setOrigin(Vec2.divideFloat(getSize(), 2));
        return rectangle;
    }

    /**
     * Creates a {@link RectangleShape} with this texture applied. The
     * size of the {@code RectangleShape} matches this texture's size.
     *
     * @return a {@link RectangleShape} representing this texture
     */
    public RectangleShape createRectangleShape() {
        return createRectangleShape(false);
    }
}
