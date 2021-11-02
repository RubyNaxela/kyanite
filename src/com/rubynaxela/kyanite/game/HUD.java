package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;

/**
 * Provides an overlay designed for being displayed on a {@link Window} over a {@link Scene}.
 */
public abstract class HUD extends RenderLayer {

    private final RectangleShape solidBackground = new RectangleShape();

    /**
     * Creates an empty HUD. Overriding this constructor is possible
     * but not recommended. Use the {@link #init} method instead.
     */
    public HUD() {
        backgroundColor = new Color(0, 0, 0, 0);
    }

    /**
     * Performs the full HUD initialization, i.a. calls the {@link #init} method. This method should not be invoked manually.
     */
    @Contract(value = "-> fail")
    public final void fullInit() {
        if (!ready) {
            ready = true;
            init();
        } else throw new IllegalStateException("This HUD has been already initialized");
    }

    /**
     * Draws every object on the window. This method is automatically executed
     * every frame by the window it belongs to and should not be invoked manually.
     *
     * @param window the window that the scene has to be displayed on
     */
    public void draw(@NotNull Window window) {
        solidBackground.setSize(Vec2.f(window.getSize()));
        solidBackground.setFillColor(backgroundColor);
        if (background != null) window.draw(background);
        else window.draw(solidBackground);
        forEach(window::draw);
    }
}
