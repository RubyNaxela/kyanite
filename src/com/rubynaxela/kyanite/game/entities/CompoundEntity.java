package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An entity consisting of many components. A {@code CompoundEntity} can be drawn to a render target,
 * as well as positioned in the scene, rotated and scaled around an origin. This class can be used,
 * for instance, to create complex structures out of primitive shapes or to compose spannable texts.
 */
public class CompoundEntity implements Transformable, Drawable {

    private final List<TransformableDrawableWrapper> objects = new ArrayList<>();
    private Vector2f position = Vector2f.ZERO, scale = Vec2.f(1, 1), origin = Vector2f.ZERO;
    private float rotation = 0;

    /**
     * Creates an empty compound entity.
     */
    public CompoundEntity() {
    }

    /**
     * Creates an empty compound entity with initial position.
     */
    public CompoundEntity(@NotNull Vector2f position) {
        setPosition(position);
    }

    /**
     * Adds an object and puts it on its relative position. Takes the current origin of the object into account.
     * It is not recommended to change the object's origin after it has been added to this {@code CompoundEntity}.
     *
     * @param object      an object of a class that implements both {@link Transformable} and {@link Drawable} interfaces
     * @param relativePos the object's position relative to the top-left corner of this compound entity
     */
    public void add(@NotNull Object object, @NotNull Vector2f relativePos) {
        if (!(object instanceof Transformable && object instanceof Drawable))
            throw new IllegalArgumentException("The object must be an instance of a class that implements both " +
                                               "org.jsfml.graphics.Transformable and org.jsfml.graphics.Drawable interfaces.");
        final TransformableDrawableWrapper obj = new TransformableDrawableWrapper(object, position);
        obj.asTransformable().setOrigin(Vector2f.add(Vector2f.sub(origin, relativePos), obj.asTransformable().getOrigin()));
        objects.add(obj);
    }

    /**
     * @return the object's global bounding rectangle in the scene, taking the object's transformation into account
     */
    public FloatRect getGlobalRect() {
        Float top = null, right = null, bottom = null, left = null;
        for (final TransformableDrawableWrapper object : objects) {
            final Object unwrapped = object.getOriginal();
            try {
                final Method getGlobalRect = unwrapped.getClass().getMethod("getGlobalBounds");
                final GlobalRect globalRect = GlobalRect.from((FloatRect) getGlobalRect.invoke(unwrapped));
                top = MathUtils.min(top, globalRect.top);
                right = MathUtils.max(right, globalRect.right);
                bottom = MathUtils.max(bottom, globalRect.bottom);
                left = MathUtils.min(left, globalRect.left);
            } catch (NoSuchMethodException e) {
                final String message = "Cannot determine global bounds for this compound entity because class " +
                                       unwrapped.getClass().getName() + " does not have a getGlobalBounds method.";
                throw new UnsupportedOperationException(message);
            } catch (InvocationTargetException | IllegalAccessException e) {
                final String message = "Cannot determine global bounds for this compound entity because the " +
                                       "getGlobalBounds method from class" + unwrapped.getClass().getName() + "is unavailable.";
                throw new UnsupportedOperationException(message);
            }
        }
        return new GlobalRect(Objects.requireNonNull(top), Objects.requireNonNull(right),
                              Objects.requireNonNull(bottom), Objects.requireNonNull(left)).toFloatRect();
    }

    /**
     * Draws a drawable object to the render target using the given render states.
     *
     * @param target the object to draw
     * @param states the render states to use for drawing
     */
    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        objects.forEach(object -> target.draw(object.asDrawable(), Objects.requireNonNull(states)));
    }

    /**
     * Sets the position of this object in the scene so that its origin will be exactly on it.
     *
     * @param x the new X coordinate.
     * @param y the new Y coordinate.
     */

    @Override
    public void setPosition(float x, float y) {
        setPosition(Vec2.f(x, y));
    }

    /**
     * Sets the position of this object in the scene so that its origin will be exactly on it.
     *
     * @param position the new position of this object
     */
    @Override
    public void setPosition(@NotNull Vector2f position) {
        this.position = position;
        objects.forEach(object -> object.setPosition(position));
    }

    /**
     * Sets the rotation of this object around its origin.
     *
     * @param angle the new rotation angle in degrees
     */
    @Override
    public void setRotation(float angle) {
        this.rotation = angle;
        objects.forEach(object -> object.asTransformable().setRotation(angle));
    }

    /**
     * Sets the scaling of this object, using its origin as the scaling center.
     *
     * @param factor the new scaling factor
     */
    public void setScale(float factor) {
        setScale(Vec2.f(factor, factor));
    }

    /**
     * Sets the scaling of this object, using its origin as the scaling center.
     *
     * @param x the new X scaling factor
     * @param y the new Y scaling factor
     */
    @Override
    public void setScale(float x, float y) {
        setScale(Vec2.f(x, y));
    }

    /**
     * Sets the scaling of this object, using its origin as the scaling center.
     *
     * @param factors the new scaling factors
     */
    @Override
    public void setScale(@NotNull Vector2f factors) {
        this.scale = factors;
        objects.forEach(object -> object.asTransformable().setScale(factors));
    }

    /**
     * Sets the rotation, scaling and drawing origin of this object.
     *
     * @param x the new X coordinate of the origin
     * @param y the new Y coordinate of the origin
     */
    @Override
    public void setOrigin(float x, float y) {
        setOrigin(Vec2.f(x, y));
    }

    /**
     * Sets the rotation, scaling and drawing origin of this object.
     *
     * @param origin the new origin
     */
    @Override
    public void setOrigin(@NotNull Vector2f origin) {
        this.origin = origin;
        objects.forEach(object -> object.asTransformable().setOrigin(Vector2f.sub(origin, object.getPosition())));
    }

    /**
     * @return the position of this object
     */
    @Override
    public Vector2f getPosition() {
        return position;
    }

    /**
     * @return the current rotation angle of this object in degrees
     */
    @Override
    public float getRotation() {
        return rotation;
    }

    /**
     * @return the current scaling factors of this object
     */
    @Override
    public Vector2f getScale() {
        return scale;
    }

    /**
     * @return the current origin of this object
     */
    @Override
    public Vector2f getOrigin() {
        return origin;
    }

    /**
     * Moves this object.
     *
     * @param x the X offset added to the current position
     * @param y the Y offset added to the current position
     */
    @Override
    public void move(float x, float y) {
        move(Vec2.f(x, y));
    }

    /**
     * Moves the object.
     *
     * @param offset the offset vector added to the current position
     */
    @Override
    public void move(@NotNull Vector2f offset) {
        setPosition(Vector2f.add(position, offset));
    }

    /**
     * Rotates this object around its origin.
     *
     * @param angle the rotation angle in degrees
     */
    @Override
    public void rotate(float angle) {
        setRotation(rotation + angle);
    }

    /**
     * Scales the object, using its origin as the scaling center. Given
     * scaling factors are multiplied by the current factors of this object.
     *
     * @param x the X scaling factor
     * @param y the Y scaling factor
     */
    @Override
    public void scale(float x, float y) {
        setScale(scale.x * x, scale.y * y);
    }

    /**
     * Scales the object, using its origin as the scaling center. Given
     * scaling factors are multiplied by the current factors of this object.
     *
     * @param factors the scaling factors
     */
    @Override
    public void scale(@NotNull Vector2f factors) {
        scale(factors.x, factors.y);
    }

    /**
     * @return the current transformation matrix of this object
     */
    @Override
    public Transform getTransform() {
        if (!objects.isEmpty()) return objects.get(0).asTransformable().getTransform();
        return null;
    }

    /**
     * @return the inverse of the current transformation matrix of this object
     */
    @Override
    public Transform getInverseTransform() {
        if (!objects.isEmpty()) return objects.get(0).asTransformable().getInverseTransform();
        return null;
    }

    private static class TransformableDrawableWrapper {

        final Object item;
        Vector2f position;

        TransformableDrawableWrapper(@NotNull Object item, @NotNull Vector2f position) {
            this.item = item;
            this.position = position;
            asTransformable().setPosition(position);
        }

        Object getOriginal() {
            return item;
        }

        Transformable asTransformable() {
            return (Transformable) item;
        }

        Drawable asDrawable() {
            return (Drawable) item;
        }

        public Vector2f getPosition() {
            return position;
        }

        public void setPosition(Vector2f position) {
            this.position = position;
            asTransformable().setPosition(position);
        }
    }
}
