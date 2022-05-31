package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.core.UnsafeOperations;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.window.BasicWindow;
import com.rubynaxela.kyanite.window.ContextSettings;
import com.rubynaxela.kyanite.window.VideoMode;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a window that can serve as a target for 2D drawing.
 */
@SuppressWarnings("deprecation")
public class RenderWindow extends org.jsfml.graphics.RenderWindow {

    private ConstView defaultView = null, view = null;

    /**
     * Constructs a new render window without actually creating (opening) it.
     *
     * @see RenderWindow#create(VideoMode, String, int, ContextSettings)
     */
    public RenderWindow() {
    }

    @Deprecated
    @SuppressWarnings("deprecation")
    RenderWindow(long ptr) {
        super(ptr);
        defaultView = new View(nativeGetDefaultView());
        view = defaultView;
    }

    /**
     * Constructs a new render window and creates it with the specified parameters.
     *
     * @param mode     the video mode to use for rendering
     * @param title    the window title
     * @param style    the window style
     * @param settings the settings for the OpenGL context
     * @see #create(VideoMode, String, int, ContextSettings)
     */
    public RenderWindow(VideoMode mode, String title, int style, ContextSettings settings) {
        create(mode, title, style, settings);
    }

    /**
     * Constructs a new render window and creates it with default context settings.
     *
     * @param mode  the video mode to use for rendering
     * @param title the window title
     * @param style the window style
     * @see #create(VideoMode, String, int)
     */
    public RenderWindow(VideoMode mode, String title, int style) {
        create(mode, title, style, new ContextSettings());
    }

    /**
     * Constructs a new render window and creates it with default style and context settings.
     *
     * @param mode  The video mode to use for rendering
     * @param title The window title
     */
    public RenderWindow(VideoMode mode, String title) {
        create(mode, title, DEFAULT, new ContextSettings());
    }

    @Override
    public void create(@NotNull VideoMode mode, @NotNull String title, int style, @NotNull ContextSettings settings) {
        super.create(mode, title, style, settings);
        defaultView = new View(nativeGetDefaultView());
        if (view == null) view = defaultView;
    }

    /**
     * Copies the current contents of the window to an image. This is a slow operation
     * and should be used to take screenshots, not to re-use resulting image as a texture.
     * For that, use {@link Texture#update(BasicWindow)} or a {@link RenderTexture} instead.
     *
     * @return the image with the current contents of the window
     */
    public Image capture() {
        final Image image = new Image(nativeCapture());
        UnsafeOperations.manageSFMLObject(image, true);
        return image;
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

    /**
     * Clears the target with black.
     */
    public void clear() {
        nativeClear(0xFF000000);
    }
}
