package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a 2D camera which defines the region of the scene that is visible.
 */
@SuppressWarnings("deprecation")
public class View extends org.jsfml.graphics.View {

    private Vector2f size = new Vector2f(1000, 1000);
    private Vector2f center = new Vector2f(500, 500);
    private float rotation = 0;
    private FloatRect viewport = new FloatRect(0, 0, 1, 1);

    private boolean transformNeedsUpdate = true;
    private Transform transformCache = null;
    private Transform inverseTransformCache = null;

    /**
     * Constructs a new view for the area of (0, 0, 1000, 1000).
     */
    public View() {
    }

    View(long wrap) {
        super(wrap);
        sync();
    }

    /**
     * Constructs a new view for the specified area.
     *
     * @param rect the area visible by this view
     */
    public View(@NotNull FloatRect rect) {
        reset(rect);
    }

    /**
     * Constructs a view for the specified area.
     *
     * @param center the center of the view
     * @param size   the size of the view
     */
    public View(@NotNull Vector2f center, @NotNull Vector2f size) {
        setCenter(center);
        setSize(size);
    }

    /**
     * Sets the center of the view.
     *
     * @param x the new X coordinate of the view's center
     * @param y the new Y coordinate of the view's center
     */
    public final void setCenter(float x, float y) {
        setCenter(new Vector2f(x, y));
    }

    /**
     * Sets the dimensions of the view.
     *
     * @param width  the new width of the view in pixels
     * @param height the new height of the view in pixels
     */
    public void setSize(float width, float height) {
        setSize(new Vector2f(width, height));
    }

    /**
     * Resets the view to a certain viewport rectangle, resetting the rotation angle in the process.
     *
     * @param rect the viewport rectangle
     */
    public void reset(@NotNull FloatRect rect) {
        nativeReset(IntercomHelper.encodeFloatRect(rect));
        sync();
    }

    private void sync() {
        nativeGetViewport(IntercomHelper.getBuffer());
        this.viewport = IntercomHelper.decodeFloatRect();
        this.size = IntercomHelper.decodeVector2f(nativeGetSize());
        this.center = IntercomHelper.decodeVector2f(nativeGetCenter());
        this.rotation = nativeGetRotation();
        transformNeedsUpdate = true;
    }

    @Override
    public Vector2f getCenter() {
        return center;
    }

    /**
     * Sets the center of the view.
     *
     * @param v the new center of the view
     */
    public void setCenter(@NotNull Vector2f v) {
        nativeSetCenter(v.x, v.y);
        this.center = v;
        transformNeedsUpdate = true;
    }

    @Override
    public Vector2f getSize() {
        return size;
    }

    /**
     * Sets the dimensions of the view.
     *
     * @param v the new size of the view in pixels
     */
    public void setSize(@NotNull Vector2f v) {
        nativeSetSize(v.x, v.y);
        this.size = v;
        transformNeedsUpdate = true;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the view around its center.
     *
     * @param angle the new rotation angle in degrees
     */
    public void setRotation(float angle) {
        nativeSetRotation(angle);
        this.rotation = angle;
        transformNeedsUpdate = true;
    }

    @Override
    public FloatRect getViewport() {
        return viewport;
    }

    /**
     * Sets the viewport rectangle of this view. The viewport defines which portion of the render
     * target is used by this view and is expressed using factors between 0 and 1 (percentage
     * of the target's width and height). The default viewport rectangle is (0, 0, 1, 1).
     *
     * @param rect the new viewport rectangle
     */
    public void setViewport(@NotNull FloatRect rect) {
        nativeSetViewport(IntercomHelper.encodeFloatRect(rect));
        this.viewport = rect;
    }

    @Override
    public Transform getTransform() {
        if (transformNeedsUpdate) updateTransform();
        return transformCache;
    }

    @Override
    public Transform getInverseTransform() {
        if (transformNeedsUpdate) updateTransform();
        return inverseTransformCache;
    }

    /**
     * Rotates the view around its center.
     *
     * @param angle the angle to rotate by, in degrees
     */
    public final void rotate(float angle) {
        setRotation(rotation + angle);
    }

    /**
     * Moves the center of the view.
     *
     * @param x the X offset to move the view's center by
     * @param y the Y offset to move the view's center by
     */
    public final void move(float x, float y) {
        setCenter(new Vector2f(center.x + x, center.y + y));
    }

    /**
     * Moves the center of the view.
     *
     * @param v the offset vector to move the view's center by
     */
    public final void move(@NotNull Vector2f v) {
        move(v.x, v.y);
    }

    /**
     * Resizes the view.
     *
     * @param factor the zoom factor
     */
    public final void zoom(float factor) {
        setSize(size.x * factor, size.y * factor);
    }

    private void updateTransform() {
        if (transformNeedsUpdate) {
            nativeGetTransform(IntercomHelper.getBuffer());
            transformCache = IntercomHelper.decodeTransform();
            inverseTransformCache = transformCache.getInverse();
            transformNeedsUpdate = false;
        }
    }
}
