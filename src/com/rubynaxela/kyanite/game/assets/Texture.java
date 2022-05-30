package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.*;

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
    private final String path;
    private boolean missing = false, suppressWarning = false;

    private Texture(@NotNull org.jsfml.graphics.Texture texture, boolean suppressWarning) {
        this.texture = texture;
        this.path = this + " (created with the copy constructor)";
        this.suppressWarning = suppressWarning;
    }

    Texture(@NotNull org.jsfml.graphics.Texture texture) {
        this(texture, false);
    }

    /**
     * Creates a new texture from the source specified by the path.
     *
     * @param path path to the source image file
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Texture(@NotNull Path path) {
        this.texture = new org.jsfml.graphics.Texture();
        this.path = path.toString();
        try {
            final File imageFile = path.toFile();
            if (ImageIO.getImageReaders(ImageIO.createImageInputStream(imageFile)).next().getFormatName().equals("JPEG")) {
                final File tmpImageFile = new File(imageFile.getParentFile(), "." + imageFile.getName() + ".png");
                final BufferedImage bufferedImage = ImageIO.read(imageFile);
                ImageIO.write(bufferedImage, "png", tmpImageFile);
                texture.loadFromFile(tmpImageFile.toPath());
                tmpImageFile.delete();
            } else texture.loadFromFile(path);
        } catch (java.io.IOException | IllegalArgumentException e) {
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
        path = this + " (created with the Texture(InputStream) constructor constructor)";
        try {
            texture.loadFromStream(stream);
        } catch (java.io.IOException | IllegalArgumentException e) {
            if (!suppressWarning) e.printStackTrace();
            missingTexture();
        }
    }

    /**
     * @return a tileable magenta-black checkboard texture typically used to indicate a texture loading error
     */
    public static Texture missing() {
        final Texture texture = new Texture(new org.jsfml.graphics.Texture(), true);
        texture.missingTexture();
        return texture;
    }

    /**
     * Applies this texture on the specified {@link Shape}. Does not affect the texture rectangle, unless the
     * texture is set to be tileable. If {@code shape} has this texture already applied, this method does nothing.
     *
     * @param shape the {@link Shape} to apply this texture on
     */
    public void apply(@NotNull Shape shape) {
        if (shape.getTexture() == texture) return;
        shape.setTexture(texture);
        final FloatRect bounds = shape.getGlobalBounds();
        if (isTileable()) {
            if (bounds.width == 0 || bounds.height == 0)
                throw new ArithmeticException("Indeterminate number of texture repetitions for shape size: [width=" +
                                              bounds.width + ";height=" + bounds.height + "]. Please set a size that " +
                                              "is non-zero on both axes before applying a tileable texture.");
            shape.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
        }
        if (missing) {
            shape.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
            if (!suppressWarning) new IOException("Missing texture: " + path).printStackTrace();
        }
    }

    /**
     * Applies this texture on the specified {@link Sprite}. Does not affect the texture rectangle, unless the
     * texture is set to be tileable. If {@code sprite} has this texture already applied, this method does nothing.1.8.
     *
     * @param sprite the {@link Sprite} to apply this texture on
     */
    public void apply(@NotNull Sprite sprite) {
        if (sprite.getTexture() == texture) return;
        sprite.setTexture(texture);
        final FloatRect bounds = sprite.getGlobalBounds();
        if (isTileable()) {
            if (bounds.width == 0 || bounds.height == 0)
                throw new ArithmeticException("Indeterminate number of texture repetitions for sprite size: [width=" +
                                              bounds.width + ";height=" + bounds.height + "]. Please set a size that " +
                                              "is non-zero on both axes before applying a tileable texture.");
            sprite.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
        }
        if (missing) {
            sprite.setTextureRect(new IntRect(0, 0, (int) bounds.width, (int) bounds.height));
            if (!suppressWarning) new IOException("Missing texture: " + path).printStackTrace();
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
        if (originAtCenter) rectangle.setOrigin(Vec2.divide(Vec2.f(getSize()), 2));
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
