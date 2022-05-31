package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

/**
 * A specialized shape representing a rectangle.
 */
@SuppressWarnings("deprecation")
public class RectangleShape extends org.jsfml.graphics.RectangleShape {

    private Vector2f size = Vector2f.ZERO;

    /**
     * Constructs a new rectangle shape with no dimensions.
     */
    public RectangleShape() {
    }

    /**
     * Constructs a new rectangle shape with the specified dimensions.
     *
     * @param size the rectangle's dimensions
     */
    public RectangleShape(@NotNull Vector2f size) {
        setSize(size);
    }

    /**
     * Gets the dimensions of the rectangle.
     *
     * @return the dimensions of the rectangle
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Sets the dimensions of the rectangle.
     *
     * @param size the new dimensions of the rectangle
     */
    public void setSize(Vector2f size) {
        nativeSetSize(size.x, size.y);
        this.size = size;
        pointsNeedUpdate = true;
    }
}
