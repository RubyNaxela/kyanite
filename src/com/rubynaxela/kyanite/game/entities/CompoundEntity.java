/*
 * Copyright (c) 2021-2022 Alex Pawelski
 *
 * Licensed under the Silicon License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://rubynaxela.github.io/Silicon-License/plain_text.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An entity consisting of many components. A {@code CompoundEntity} can be drawn to a render target, as well as
 * positioned in the scene, rotated and scaled around an origin. The collection index of an element is also its z-index.
 * If two elements overlap each other, the one with the higher index will be displayed over the other one. This class
 * can be used, for instance, to create complex structures out of primitive shapes or to compose spannable texts.
 */
public class CompoundEntity implements Drawable, Transformable {

    private final List<Drawable> components = new ArrayList<>();
    private float rotation = 0;
    private Vector2f origin = Vector2f.zero(), position = Vector2f.zero(), scale = Vec2.f(1, 1);
    private int layer = 0;

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
        if (components.isEmpty()) return null;
        Float top = null, right = null, bottom = null, left = null;
        for (final Drawable component : components) {
            final FloatRect bounds = getGlobalBounds(component, this);
            left = MathUtils.min(left, bounds.left);
            top = MathUtils.min(top, bounds.top);
            right = MathUtils.max(right, bounds.right);
            bottom = MathUtils.max(bottom, bounds.bottom);
        }
        return FloatRect.fromCoordinates(left, top, right, bottom);
    }

    /**
     * Tests whether any of the global bounds of this {@code CompoundEntity}'s components intersect with any of
     * the global bounds of the parameter {@code CompoundEntity}'s components This method's time complexity is
     * <i>O(m*n)</i>, where <i>m</i> and <i>n</i>  are the numbers of this and the other's {@code CompoundEntity}'s
     * components. Using it on {@code CompoundEntity}s with many components might cause performance issues.
     *
     * @param other the other {@code CompoundEntity}
     * @return whether any of this {@code CompoundEntity} components intersects
     * with any of the parameter {@code CompoundEntity}'s components
     * @throws UnsupportedOperationException if any of the component's global bounds cannot be determined
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

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * @return a list of objects that this compound entity consists of
     */
    public List<Drawable> getComponents() {
        return new ArrayList<>(components);
    }

    /**
     * @return the position of this object
     */
    @Override
    public Vector2f getPosition() {
        return position;
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
     * @return the current rotation angle of this object in degrees
     */
    @Override
    public float getRotation() {
        return rotation;
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
     * @return the current scaling factors of this object
     */
    @Override
    public Vector2f getScale() {
        return scale;
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
     * @return the current origin of this object
     */
    @Override
    public Vector2f getOrigin() {
        return origin;
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
     * Moves the object.
     *
     * @param offset the offset vector added to the current position
     */
    @Override
    public void move(@NotNull Vector2f offset) {
        setPosition(Vec2.add(position, offset));
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
     * @param scale the scaling factors
     */
    @Override
    public void scale(@NotNull Vector2f scale) {
        scale(scale.x, scale.y);
    }

    /**
     * Scales the object, using its origin as the scaling center. Given
     * scaling factor is multiplied by the current factors of this object.
     *
     * @param factor the scaling factor
     */
    @Override
    public void scale(float factor) {
        scale(factor, factor);
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
     * @return the current transformation matrix of this object
     */
    @Override
    public Transform getTransform() {
        final Vector2f localOrigin = Vec2.add(position, origin);
        return MathUtils.combineTransforms(Transform.rotate(Transform.IDENTITY, rotation, localOrigin),
                                           Transform.scale(Transform.IDENTITY, scale, localOrigin),
                                           Transform.translate(Transform.IDENTITY, localOrigin));
    }

    /**
     * @return the inverse of the current transformation matrix of this object
     */
    @Override
    public Transform getInverseTransform() {
        return getTransform().getInverse();
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
}
