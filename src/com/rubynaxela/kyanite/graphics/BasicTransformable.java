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

package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

/**
 * Implementation of the {@link Transformable} interface. Classes can inherit from this in order to provide
 * the {@link Transformable} interface. This implementation equals that of the original SFML implementation.
 */
public class BasicTransformable implements Transformable {

    private Vector2f origin = Vector2f.ZERO;
    private Vector2f position = Vector2f.ZERO;
    private float rotation = 0;
    private Vector2f scale = new Vector2f(1, 1);

    private Transform transform = Transform.IDENTITY;
    private boolean transformNeedsUpdate = false;
    private Transform inverseTransform = Transform.IDENTITY;
    private boolean inverseTransformNeedsUpdate = false;

    /**
     * Default constructor, initializes this object with an identity transformation.
     */
    public BasicTransformable() {
    }

    @Override
    public final void setPosition(float x, float y) {
        setPosition(new Vector2f(x, y));
    }

    @Override
    public final void setScale(float x, float y) {
        setScale(new Vector2f(x, y));
    }

    @Override
    public final void setOrigin(float x, float y) {
        setOrigin(new Vector2f(x, y));
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public void setPosition(@NotNull Vector2f v) {
        this.position = v;

        transformNeedsUpdate = true;
        inverseTransformNeedsUpdate = true;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float angle) {
        this.rotation = angle % 360.0f;
        if (this.rotation < 0)
            this.rotation += 360.0f;

        transformNeedsUpdate = true;
        inverseTransformNeedsUpdate = true;
    }

    @Override
    public Vector2f getScale() {
        return scale;
    }

    @Override
    public void setScale(@NotNull Vector2f factors) {
        this.scale = factors;

        transformNeedsUpdate = true;
        inverseTransformNeedsUpdate = true;
    }

    @Override
    public Vector2f getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(@NotNull Vector2f v) {
        this.origin = v;

        transformNeedsUpdate = true;
        inverseTransformNeedsUpdate = true;
    }

    @Override
    public final void move(float x, float y) {
        move(new Vector2f(x, y));
    }

    @Override
    public void move(@NotNull Vector2f v) {
        setPosition(Vec2.add(position, v));
    }

    @Override
    public void rotate(float angle) {
        setRotation(rotation + angle);
    }

    @Override
    public final void scale(float x, float y) {
        scale(new Vector2f(x, y));
    }

    @Override
    public void scale(@NotNull Vector2f factors) {
        setScale(Vec2.multiply(scale, factors));
    }

    @Override
    public Transform getTransform() {
        if (transformNeedsUpdate) {
            final double angle = -Math.toRadians(rotation);
            final float cos = (float) Math.cos(angle);
            final float sin = (float) Math.sin(angle);

            final float sxc = scale.x * cos;
            final float syc = scale.y * cos;
            final float sxs = scale.x * sin;
            final float sys = scale.y * sin;
            final float tx = -origin.x * sxc - origin.y * sys + position.x;
            final float ty = origin.x * sxs - origin.y * syc + position.y;

            transform = new Transform(
                    sxc, sys, tx,
                    -sxs, syc, ty,
                    0, 0, 1);

            transformNeedsUpdate = false;
        }

        return transform;
    }

    @Override
    public Transform getInverseTransform() {
        if (inverseTransformNeedsUpdate) {
            inverseTransform = getTransform().getInverse();
            inverseTransformNeedsUpdate = false;
        }

        return inverseTransform;
    }
}
