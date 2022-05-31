package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.Const;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vector2f;

/**
 * Interface for read-only views. It provides methods to can gain information from a view,
 * but none to modify it in any way. Note that this interface is expected to be implemented
 * by a {@link View}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstView extends Const {

    /**
     * Gets the current center of the view.
     *
     * @return the current center of the view
     */
    Vector2f getCenter();

    /**
     * Gets the current dimensions of the view in pixels.
     *
     * @return the current dimensions of the view in pixels
     */
    Vector2f getSize();

    /**
     * Gets the current rotation angle of the view in degrees.
     *
     * @return the current rotation angle of the view in degrees
     */
    float getRotation();

    /**
     * Gets the current viewport rectangle of this view.
     *
     * @return the current viewport rectangle of this view
     */
    FloatRect getViewport();

    /**
     * Gets the view's current transformation matrix as determined by its center,
     * size and rotation angle.
     *
     * @return the view's current transformation matrix
     */
    Transform getTransform();

    /**
     * Gets the inverse of the view's current transformation matrix as determined
     * by its center, size and rotation angle.
     *
     * @return the inverse of the view's current transformation matrix
     */
    Transform getInverseTransform();
}
