package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for render targets.
 */
public interface RenderTarget {

    /**
     * Clears the render target and fills it with a constant color.
     *
     * @param color the color to fill the target with
     */
    void clear(@NotNull Color color);

    /**
     * Gets the target's current view.
     *
     * @return the target's current view
     */
    ConstView getView();

    /**
     * Sets the target's view.
     *
     * @param view the target's new view
     */
    void setView(ConstView view);

    /**
     * Gets the target's default view.
     *
     * @return the target's default view
     */
    ConstView getDefaultView();

    /**
     * Computes the viewport of the given view applied to this render target.
     *
     * @param view the view
     * @return the viewport of the given view applied to this render target
     */
    default IntRect getViewport(ConstView view) {
        final FloatRect viewport = view.getViewport();
        final Vector2i size = getSize();
        return new IntRect((int) (0.5f + viewport.left * size.x), (int) (0.5f + viewport.top * size.y),
                           (int) (viewport.width * size.x), (int) (viewport.height * size.y));
    }

    /**
     * Converts a point from target coordinates to world coordinates using the target's current view.
     *
     * @param point the point on the render target
     * @return the matching point in the world
     */
    Vector2f mapPixelToCoords(@NotNull Vector2i point);

    /**
     * Converts a point from target coordinates to world coordinates for a certain view.
     *
     * @param point the point on the render target
     * @param view  the view to use for conversion
     * @return the matching point in the world
     */
    Vector2f mapPixelToCoords(@NotNull Vector2i point, @NotNull ConstView view);

    /**
     * Converts a point from world coordinates to target coordinates using the target's
     * current view.
     *
     * @param point the point in the world
     * @return the matching point on the render target
     */
    Vector2i mapCoordsToPixel(@NotNull Vector2f point);

    /**
     * Converts a point from world coordinates to target coordinates for a certain view.
     *
     * @param point the point in the world
     * @param view  the view to use for conversion
     * @return the matching point on the render target
     */
    Vector2i mapCoordsToPixel(@NotNull Vector2f point, @NotNull ConstView view);

    /**
     * Draws a drawable object to the render target using the default render states.
     *
     * @param drawable the object to draw
     */
    default void draw(@NotNull Drawable drawable) {
        draw(drawable, RenderStates.DEFAULT);
    }

    /**
     * Draws a drawable object to the render target using the given render states.
     *
     * @param drawable     the object to draw
     * @param renderStates the render states to use for drawing
     */
    default void draw(@NotNull Drawable drawable, @NotNull RenderStates renderStates) {
        drawable.draw(this, renderStates);
    }

    /**
     * Draws a primitive of the given type using the given vertices and the default render states.
     *
     * @param vertices the vertices to draw
     * @param type     the type of OpenGL primitive to draw
     */
    default void draw(@NotNull Vertex[] vertices, @NotNull PrimitiveType type) {
        draw(vertices, type, RenderStates.DEFAULT);
    }

    /**
     * Draws a primitive of the given type using the given vertices and the given render states.
     *
     * @param vertices the vertices to draw
     * @param type     the type of OpenGL primitive to draw
     * @param states   the render states to use for drawing
     */
    default void draw(@NotNull Vertex[] vertices, @NotNull PrimitiveType type, @NotNull RenderStates states) {
        SFMLNativeDrawer.drawVertices(vertices, type, this, states);
    }

    /**
     * Gets the size of the render region.
     *
     * @return the size of the render region
     */
    Vector2i getSize();

    /**
     * Pushes the current OpenGL states and matrices to the stack.
     */
    void pushGLStates();

    /**
     * Pops the last OpenGL states and matrices from the top of the stack.
     */
    void popGLStates();

    /**
     * Resets the internal OpenGL states so that the target is ready for drawing.
     */
    void resetGLStates();
}
