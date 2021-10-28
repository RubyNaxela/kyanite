package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;

/**
 * Provides a set of references to the basic objects used by the game. The instance of this class can be accessed via the
 * {@link GameContext#getInstance} method. Only one {@code GameContext} instance can exist in a single game instance.
 */
public final class GameContext {

    private static final GameContext instance = new GameContext();
    private final AssetsBundle assetsBundle;
    private final Clock clock;
    private Window window;

    private GameContext() {
        assetsBundle = new AssetsBundle();
        clock = new Clock(false);
    }

    /**
     * @return reference to the instance of the game context
     */
    public static GameContext getInstance() {
        return instance;
    }

    /**
     * Sets the window size and title. This has to be called before the window is used.
     *
     * @param size  the window size
     * @param title the window title
     * @return the reference to the window
     */
    @Contract("_, _ -> new")
    public Window setupWindow(@NotNull Vector2i size, @NotNull String title) {
        if (window != null) throw new IllegalStateException("The window has been already initialized");
        return window = new Window(new VideoMode(size.x, size.y), title);
    }

    /**
     * Sets the window size and title. This has to be called before the window is used.
     *
     * @param width  the window width
     * @param height the window height
     * @param title  the window title
     * @return the reference to the window
     */
    @Contract("_, _, _ -> new")
    public Window setupWindow(int width, int height, @NotNull String title) {
        return setupWindow(Vec2.i(width, height), title);
    }

    /**
     * Sets the window size. This has to be called before the window is used.
     *
     * @param size the window size
     * @return the reference to the window
     */
    @Contract("_ -> new")
    public Window setupWindow(@NotNull Vector2i size) {
        return setupWindow(size, "");
    }

    /**
     * Sets the window size. This has to be called before the window is used.
     *
     * @param width  the window width
     * @param height the window height
     * @return the reference to the window
     */
    @Contract("_, _ -> new")
    public Window setupWindow(int width, int height) {
        return setupWindow(Vec2.i(width, height), "");
    }

    /**
     * @return reference to the game window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * @return reference to the game assets bundle
     */
    @NotNull
    public AssetsBundle getAssetsBundle() {
        return assetsBundle;
    }

    /**
     * @return reference to the game clock
     */
    @NotNull
    public Clock getClock() {
        return clock;
    }
}
