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

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

/**
 * Decomposed transform defined by a position, a rotation and a scale.
 */
@SuppressWarnings("deprecation")
public abstract class SFMLNativeTransformable extends org.jsfml.graphics.SFMLNativeTransformable implements Transformable {

    private Vector2f position = Vector2f.zero(), scale = Vec2.f(1, 1), origin = Vector2f.zero();
    private float rotation = 0;
    private boolean transformNeedsUpdate = true;
    private Transform transformCache = null, inverseTransformCache = null;

    protected SFMLNativeTransformable() {
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public void setPosition(@NotNull Vector2f position) {
        this.position = position;
        nativeSetPosition(position.x, position.y);
        transformNeedsUpdate = true;
    }

    @Override
    public final void setPosition(float x, float y) {
        setPosition(Vec2.f(x, y));
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float angle) {
        rotation = angle;
        nativeSetRotation(angle);
        transformNeedsUpdate = true;
    }

    @Override
    public Vector2f getScale() {
        return scale;
    }

    @Override
    public void setScale(@NotNull Vector2f scale) {
        this.scale = scale;
        nativeSetScale(scale.x, scale.y);
        transformNeedsUpdate = true;
    }

    public final void setScale(float factor) {
        setScale(Vec2.f(factor, factor));
    }

    @Override
    public final void setScale(float x, float y) {
        setScale(Vec2.f(x, y));
    }

    @Override
    public Vector2f getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(@NotNull Vector2f origin) {
        this.origin = origin;
        nativeSetOrigin(origin.x, origin.y);
        transformNeedsUpdate = true;
    }

    @Override
    public final void setOrigin(float x, float y) {
        setOrigin(Vec2.f(x, y));
    }

    @Override
    public final void move(@NotNull Vector2f offset) {
        move(offset.x, offset.y);
    }

    @Override
    public final void move(float x, float y) {
        setPosition(Vec2.f(position.x + x, position.y + y));
    }

    @Override
    public final void rotate(float angle) {
        setRotation(rotation + angle);
    }

    @Override
    public final void scale(@NotNull Vector2f scale) {
        scale(scale.x, scale.y);
    }

    public final void scale(float factor) {
        setScale(Vec2.f(scale.x * factor, scale.y * factor));
    }

    @Override
    public final void scale(float x, float y) {
        setScale(Vec2.f(scale.x * x, scale.y * y));
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

    private void updateTransform() {
        if (transformNeedsUpdate) {
            nativeGetTransform(IntercomHelper.getBuffer());
            transformCache = IntercomHelper.decodeTransform();
            inverseTransformCache = transformCache.getInverse();
            transformNeedsUpdate = false;
        }
    }
}
