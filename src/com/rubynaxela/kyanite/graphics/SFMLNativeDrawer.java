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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Provides generic implementations of the {@code draw} methods for native SFML drawables and vertices.
 */
@SuppressWarnings("deprecation")
public final class SFMLNativeDrawer extends org.jsfml.graphics.SFMLNativeDrawer {

    private static final int NATIVE_VERTEX_SIZE = 20;
    private static final int MAX_VERTICES = 1024;

    private static final ThreadLocal<ByteBuffer> vertexBuffer = ThreadLocal.withInitial(
            () -> ByteBuffer.allocateDirect(MAX_VERTICES * NATIVE_VERTEX_SIZE).order(ByteOrder.nativeOrder()));

    public static void drawVertices(Vertex[] vertices, PrimitiveType type, RenderTarget target, RenderStates states) {
        final ByteBuffer vbuf = vertexBuffer.get();
        final FloatBuffer vfloats = vbuf.asFloatBuffer();
        final IntBuffer vints = vbuf.asIntBuffer();
        for (int i = 0; i < vertices.length; i++) {
            final Vertex v = vertices[i];
            final int index = i * NATIVE_VERTEX_SIZE / 4;
            vfloats.put(index, v.position.x);
            vfloats.put(index + 1, v.position.y);
            vints.put(index + 2, IntercomHelper.encodeColor(v.color));
            vfloats.put(index + 3, v.texCoords.x);
            vfloats.put(index + 4, v.texCoords.y);
        }
        nativeDrawVertices(vertices.length, vbuf, type.ordinal(), target, states.blendMode.ordinal(),
                           IntercomHelper.encodeTransform(states.transform), states.texture, states.shader);
    }

    static void draw(Drawable drawable, RenderTarget target, RenderStates states) {
        nativeDrawDrawable(drawable, target, states.blendMode.ordinal(), IntercomHelper.encodeTransform(states.transform),
                           states.texture, states.shader);
    }
}
