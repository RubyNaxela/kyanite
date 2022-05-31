package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.*;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.system.IOException;
import com.rubynaxela.kyanite.window.BasicWindow;
import com.rubynaxela.kyanite.window.Context;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Represents a 2D texture stored on the graphics card for rendering.
 */
@SuppressWarnings("deprecation")
public class Texture extends org.jsfml.graphics.Texture {

    private static final int MAXIMUM_SIZE = nativeGetMaximumSize();

    static {
        SFMLNative.loadNativeLibraries();
    }

    private Vector2i size = Vector2i.ZERO;
    private boolean smooth = false;
    private boolean repeated = false;

    /**
     * Constructs a new texture.
     */
    public Texture() {
    }

    @SuppressWarnings("deprecation")
    Texture(long wrap) {
        super(wrap);
        updateSize();
        smooth = nativeIsSmooth();
        repeated = nativeIsRepeated();
    }

    /**
     * Constructs a new texture by copying another texture.
     *
     * @param other the texture to copy
     */
    @SuppressWarnings("deprecation")
    public Texture(ConstTexture other) {
        super(((Texture) other).nativeCopy());
        UnsafeOperations.manageSFMLObject(this, true);
        updateSize();
        smooth = nativeIsSmooth();
        repeated = nativeIsRepeated();
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
    public static void bind(@NotNull ConstTexture texture, @NotNull CoordinateType coordinateType) {
        nativeBind((Texture) texture, coordinateType.ordinal());
    }

    /**
     * Activates a texture for rendering, using the {@link Texture.CoordinateType#NORMALIZED} coordinate type.
     */
    public static void bind(@NotNull ConstTexture texture) {
        bind(texture, CoordinateType.NORMALIZED);
    }

    /**
     * Generates an empty texture with the specified dimensions.
     *
     * @param width  the texture's width
     * @param height the texture's height
     * @throws TextureCreationException if the texture could not be created
     */
    public void create(int width, int height) throws TextureCreationException {
        if (!nativeCreate(width, height)) throw new TextureCreationException("Failed to create texture.");
        updateSize();
    }

    /**
     * Fully loads all available bytes from an {@link InputStream} and attempts to load the texture portion from it.
     *
     * @param in   the input stream to read from
     * @param area the area of the image to load into the texture
     * @throws IOException in case an I/O error occurs
     */
    public void loadFromStream(@NotNull InputStream in, @NotNull IntRect area) throws IOException {
        Context.getContext();
        final SFMLInputStream.NativeStreamRef streamRef = new SFMLInputStream.NativeStreamRef();
        streamRef.initialize(new SFMLInputStream(Objects.requireNonNull(in)));
        SFMLErrorCapture.start();
        final boolean success = nativeLoadFromStream(streamRef, IntercomHelper.encodeIntRect(area));
        final String msg = SFMLErrorCapture.finish();
        if (!success) throw new IOException(msg);
        updateSize();
    }

    /**
     * Fully loads all available bytes from an {@link InputStream} and attempts to load the texture from it.
     *
     * @param in the input stream to read from
     * @throws IOException in case an I/O error occurs
     */
    public final void loadFromStream(@NotNull InputStream in) throws IOException {
        loadFromStream(in, IntRect.EMPTY);
    }

    /**
     * Attempts to load the texture from a file.
     *
     * @param path the path to the file to load the texture from
     * @param area the area of the image to load into the texture
     * @throws IOException in case an I/O error occurs
     */
    public void loadFromFile(@NotNull Path path, @NotNull IntRect area) throws IOException {
        Context.getContext();
        SFMLErrorCapture.start();
        final boolean success = nativeLoadFromFile(path.toAbsolutePath().toString(), IntercomHelper.encodeIntRect(area));
        final String msg = SFMLErrorCapture.finish();
        if (!success) throw new IOException(msg);
        updateSize();
    }

    /**
     * Attempts to load the texture from a file.
     *
     * @param path the path to the file to load the texture from
     * @throws IOException in case an I/O error occurs
     */
    public final void loadFromFile(@NotNull Path path) throws IOException {
        loadFromFile(path, IntRect.EMPTY);
    }

    /**
     * Attempts to load the texture from a source image portion.
     *
     * @param image the source image
     * @param area  the area of the image to load into the texture
     * @throws TextureCreationException if the texture could not be loaded from the image
     */
    public void loadFromImage(@NotNull Image image, @NotNull IntRect area) throws TextureCreationException {
        image.commit();
        Context.getContext();
        if (!nativeLoadFromImage(image, IntercomHelper.encodeIntRect(area)))
            throw new TextureCreationException("Failed to load texture from image.");
        updateSize();
    }

    /**
     * Attempts to load the texture from a source image.
     *
     * @param image the source image
     * @throws TextureCreationException if the texture could not be loaded from the image
     */
    public final void loadFromImage(@NotNull Image image) throws TextureCreationException {
        loadFromImage(image, IntRect.EMPTY);
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
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * Enables or disables texture repeating.
     * <p/>
     * Texture repeating is disabled by default.
     *
     * @param repeated {@code true} to enable, {@code false} to disable.
     */
    public void setRepeated(boolean repeated) {
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
