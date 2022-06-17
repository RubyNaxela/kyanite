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

package com.rubynaxela.kyanite.core;

import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.graphics.Transform;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;
import org.jetbrains.annotations.NotNull;

import java.nio.*;

/**
 * Provides functionality to encode and decode data structures used for intercom methods.
 */
public final class IntercomHelper {

    private static final ThreadLocal<ByteBuffer> BUFFER =
            ThreadLocal.withInitial(() -> ByteBuffer.allocateDirect(256).order(ByteOrder.nativeOrder()));

    private IntercomHelper() {
    }

    /**
     * Gets the current thread-local buffer.
     *
     * @return the current thread-local buffer
     */
    public static ByteBuffer getBuffer() {
        return BUFFER.get();
    }

    /**
     * Encodes a color into a 32-bit integer.
     *
     * @param color the color to encode
     * @return the encoded color
     */
    public static int encodeColor(@NotNull Color color) {
        return ((int) color.a << 24) | ((int) color.b << 16) | (color.g << 8) | color.r;
    }

    /**
     * Decodes a color from a 32-bit integer.
     *
     * @param code the encoded color
     * @return the decoded color
     */
    public static Color decodeColor(int code) {
        final int a = (code >> 24) & 0xFF;
        final int b = (code >> 16) & 0xFF;
        final int g = (code >> 8) & 0xFF;
        final int r = code & 0xFF;
        return new Color(r, g, b, a);
    }

    /**
     * Encodes an integer vector into a 64-bit integer.
     *
     * @param vector the vector
     * @return the encoded vector
     */
    public static long encodeVector2i(@NotNull Vector2i vector) {
        long v = ((long) vector.y) << 32;
        v |= vector.x;
        return v;
    }

    /**
     * Encodes a float vector into a 64-bit integer.
     *
     * @param vector the vector
     * @return the encoded vector
     */
    public static long encodeVector2f(@NotNull Vector2f vector) {
        long v = ((long) Float.floatToIntBits(vector.y)) << 32;
        v |= Float.floatToIntBits(vector.x);
        return v;
    }

    /**
     * Decodes an integer vector from a 64-bit integer.
     *
     * @param vector the encoded vector
     * @return the decoded vector
     */
    public static Vector2i decodeVector2i(long vector) {
        return vector == 0 ? Vector2i.ZERO : new Vector2i((int) vector, (int) (vector >> 32));
    }

    /**
     * Decodes a float vector from a 64-bit integer.
     *
     * @param vector the encoded vector
     * @return the decoded vector
     */
    public static Vector2f decodeVector2f(long vector) {
        return new Vector2f(Float.intBitsToFloat((int) vector), Float.intBitsToFloat((int) (vector >> 32)));
    }

    /**
     * Decodes an integer rectangle from the current float buffer content.
     *
     * @return the decoded rectangle
     */
    public static IntRect decodeIntRect() {
        final IntBuffer buf = BUFFER.get().asIntBuffer();
        return new IntRect(buf.get(0), buf.get(1), buf.get(2), buf.get(3));
    }

    /**
     * Encodes an integer rectangle into the current integer buffer.
     *
     * @param rectangle the rectangle to encode
     * @return A reference to the integer buffer
     */
    public static Buffer encodeIntRect(@NotNull IntRect rectangle) {
        final IntBuffer buf = BUFFER.get().asIntBuffer();
        buf.put(0, rectangle.left);
        buf.put(1, rectangle.top);
        buf.put(2, rectangle.width);
        buf.put(3, rectangle.height);
        return buf;
    }

    /**
     * Decodes a float rectangle from the current float buffer content.
     *
     * @return the decoded rectangle
     */
    public static FloatRect decodeFloatRect() {
        final FloatBuffer buf = BUFFER.get().asFloatBuffer();
        return new FloatRect(buf.get(0), buf.get(1), buf.get(2), buf.get(3));
    }

    /**
     * Encodes a float rectangle into the current float buffer.
     *
     * @param rectangle the float to encode
     * @return A reference to the float buffer
     */
    public static Buffer encodeFloatRect(@NotNull FloatRect rectangle) {
        final FloatBuffer buf = BUFFER.get().asFloatBuffer();
        buf.put(0, rectangle.left);
        buf.put(1, rectangle.top);
        buf.put(2, rectangle.width);
        buf.put(3, rectangle.height);
        return buf;
    }

    /**
     * Decodes a transformation matrix from the current float buffer content.
     *
     * @return the decoded transformation matrix
     */
    public static Transform decodeTransform() {
        final FloatBuffer buf = BUFFER.get().asFloatBuffer();
        return new Transform(
                buf.get(0), buf.get(1), buf.get(2),
                buf.get(3), buf.get(4), buf.get(5),
                buf.get(6), buf.get(7), buf.get(8));
    }

    /**
     * Encodes a transformation matrix into the current float buffer.
     *
     * @param matrix the transformation matrix to encode
     * @return A reference to the float buffer
     */
    public static Buffer encodeTransform(@NotNull Transform matrix) {
        final FloatBuffer buf = BUFFER.get().asFloatBuffer();
        final float[] data = matrix.getMatrix();
        buf.put(0, data[0]);
        buf.put(1, data[4]);
        buf.put(2, data[12]);
        buf.put(3, data[1]);
        buf.put(4, data[5]);
        buf.put(5, data[13]);
        buf.put(6, data[3]);
        buf.put(7, data[7]);
        buf.put(8, data[15]);
        return buf;
    }
}
