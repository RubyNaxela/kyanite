package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Decomposed transform defined by a position, a rotation and a scale.
 */
@SuppressWarnings("deprecation")
public abstract class SFMLNativeTransformable extends org.jsfml.graphics.SFMLNativeTransformable {

    private Vector2f position = Vector2f.ZERO;
    private float rotation = 0;
    private Vector2f scale = new Vector2f(1, 1);
    private Vector2f origin = Vector2f.ZERO;

    private boolean transformNeedsUpdate = true;
    private Transform transformCache = null;
    private Transform inverseTransformCache = null;

    protected SFMLNativeTransformable() {
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
        position = Objects.requireNonNull(v);
        nativeSetPosition(v.x, v.y);
        transformNeedsUpdate = true;
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
    public void setScale(@NotNull Vector2f v) {
        scale = Objects.requireNonNull(v);
        nativeSetScale(v.x, v.y);
        transformNeedsUpdate = true;
    }

    @Override
    public Vector2f getOrigin() {
        return origin;
    }

    @Override
    public void setOrigin(@NotNull Vector2f v) {
        origin = Objects.requireNonNull(v);
        nativeSetOrigin(v.x, v.y);
        transformNeedsUpdate = true;
    }

    @Override
    public final void move(float x, float y) {
        setPosition(new Vector2f(position.x + x, position.y + y));
    }

    @Override
    public final void move(@NotNull Vector2f v) {
        move(v.x, v.y);
    }

    @Override
    public final void rotate(float angle) {
        setRotation(rotation + angle);
    }

    @Override
    public final void scale(float x, float y) {
        setScale(new Vector2f(scale.x * x, scale.y * y));
    }

    @Override
    public final void scale(@NotNull Vector2f factors) {
        scale(factors.x, factors.y);
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
