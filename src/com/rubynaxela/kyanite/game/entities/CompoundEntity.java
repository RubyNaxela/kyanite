package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An entity consisting of many components. A {@code CompoundEntity} can be drawn to a render target, as well as
 * positioned in the scene, rotated and scaled around an origin. The collection index of an element is also its z-index.
 * If two elements overlap each other, the one with the higher index will be displayed over the other one. This class
 * can be used, for instance, to create complex structures out of primitive shapes or to compose spannable texts.
 */
public class CompoundEntity implements Drawable, Transformable {

    private final List<Drawable> components = new ArrayList<>();
    private float rotation = 0;
    private Vector2f origin = Vector2f.ZERO, position = Vector2f.ZERO, scale = Vec2.f(1, 1);

    /**
     * Creates an empty compound entity.
     */
    public CompoundEntity() {
    }

    /**
     * Creates an empty compound entity with initial position.
     *
     * @param position the initial position of this compound entity
     */
    public CompoundEntity(@NotNull Vector2f position) {
        setPosition(position);
    }

    @NotNull
    private static FloatRect getGlobalBounds(@NotNull Drawable component, @NotNull CompoundEntity compoundEntity) {
        try {
            final Method getGlobalBounds = component.getClass().getMethod("getGlobalBounds");
            return compoundEntity.getTransform().transformRect((FloatRect) getGlobalBounds.invoke(component));
        } catch (NoSuchMethodException e) {
            final String message = "Cannot determine global bounds for this compound entity because class " +
                                   component.getClass().getName() + " does not have a getGlobalBounds method.";
            throw new UnsupportedOperationException(message);
        } catch (InvocationTargetException | IllegalAccessException e) {
            final String message = "Cannot determine global bounds for this compound entity because the " +
                                   "getGlobalBounds method from class" + component.getClass().getName() + "is unavailable.";
            throw new UnsupportedOperationException(message);
        }
    }

    /**
     * Adds the specified component objects to this compound entity.
     *
     * @param objects {@link Drawable} components
     */
    public void add(@NotNull Drawable... objects) {
        components.addAll(Arrays.asList(objects));
    }

    /**
     * @return the object's global bounding rectangle in the scene, taking the entity's transformation
     * into account, or {@code null} if this {@code CompoundEntity} has no components
     * @throws UnsupportedOperationException if any of the component's global bounds cannot be determined
     */
    public FloatRect getGlobalBounds() {
        Float top = null, right = null, bottom = null, left = null;
        for (final Drawable component : components) {
            final GlobalRect globalRect = GlobalRect.from(getGlobalBounds(component, this));
            top = MathUtils.min(top, globalRect.top);
            right = MathUtils.max(right, globalRect.right);
            bottom = MathUtils.max(bottom, globalRect.bottom);
            left = MathUtils.min(left, globalRect.left);
        }
        try {
            return new GlobalRect(Objects.requireNonNull(top), Objects.requireNonNull(right),
                                  Objects.requireNonNull(bottom), Objects.requireNonNull(left)).toFloatRect();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Tests whether any of the global bounds of this {@code CompoundEntity}'s components intersect
     * with any of the global bounds of the parameter {@code CompoundEntity}'s components
     *
     * @param other the other {@code CompoundEntity}
     * @return whether any of this {@code CompoundEntity} components intersects
     * with any of the parameter {@code CompoundEntity}'s components
     * @throws UnsupportedOperationException if any of the component's global bounds cannot be determined
     * @apiNote This method's time complexity is O(m*n), where m and n are the numbers
     * of this and the other's {@code CompoundEntity}'s components. Using it on
     * {@code CompoundEntity}s with many components can cause performance issues.
     */
    public boolean intersects(@NotNull CompoundEntity other) {
        for (final Drawable component : components)
            for (final Drawable otherComponent : other.components)
                if ((getGlobalBounds(component, this)).intersection(getGlobalBounds(otherComponent, other)) != null)
                    return true;
        return false;
    }

    /**
     * Draws a drawable object to the render target using the given render states.
     *
     * @param target the object to draw
     * @param states the render states to use for drawing
     */
    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        final RenderStates renderStates = new RenderStates(states.blendMode,
                                                           Transform.combine(states.transform, getTransform()),
                                                           states.texture, states.shader);
        components.forEach(component -> target.draw(component, renderStates));
    }

    /**
     * @return a list of objects that this compound entity consists of
     */
    public List<Drawable> getComponents() {
        return new ArrayList<>(components);
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
     * Scales the object, using its origin as the scaling center. Given
     * scaling factor is multiplied by the current factors of this object.
     *
     * @param factor the scaling factor
     */
    public void scale(float factor) {
        scale(factor, factor);
    }

    /**
     * Sets the position of this object in the scene so that its origin will be exactly on it.
     *
     * @param position the new position of this object
     */
    @Override
    public void setPosition(@NotNull Vector2f position) {
        this.position = position;
    }

    /**
     * Sets the rotation of this object around its origin.
     *
     * @param angle the new rotation angle in degrees
     */
    @Override
    public void setRotation(float angle) {
        this.rotation = angle;
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
        final Vector2f localOrigin = Vec2.add(position, origin);
        return MathUtils.combineTransforms(Transform.rotate(Transform.IDENTITY, rotation, localOrigin),
                                           Transform.scale(Transform.IDENTITY, scale, localOrigin),
                                           Transform.translate(Transform.IDENTITY, Vec2.subtract(position, origin)));
    }

    /**
     * @return the inverse of the current transformation matrix of this object
     */
    @Override
    public Transform getInverseTransform() {
        return getTransform().getInverse();
    }
}
