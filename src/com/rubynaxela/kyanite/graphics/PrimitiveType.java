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

/**
 * Enumeration of supported primitive types.
 *
 * @see VertexArray
 */
public enum PrimitiveType {

    /**
     * A list of individual points.
     */
    POINTS,
    /**
     * A list of individual lines.
     */
    LINES,
    /**
     * A list of connected lines, where each point gets connected to the previous point.
     */
    LINE_STRIP,
    /**
     * A list of individual triangles.
     */
    TRIANGLES,
    /**
     * A list of connected triangles, where ech point uses the two previous points to form a triangle.
     */
    TRIANGLE_STRIP,
    /**
     * A list of connected triangles, where each point uses the common center and the previous point to form a triangle.
     */
    TRIANGLE_FAN,
    /**
     * A list of individual quads.
     */
    QUADS
}
