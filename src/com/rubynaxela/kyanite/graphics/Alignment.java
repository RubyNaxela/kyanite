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
 * Values for the alignment mode. The first word of the name indicates vertical alignment and the
 * second word indicates horizontal alignment. The {@code CENTER} value indicates centering on both axes.
 * The alignment determines where (relative to the text) is the origin point of that text object.
 *
 * @see Text#setAlignment
 */
public enum Alignment {
    /**
     * Horizontal alignment: left; vertical alignment: top.
     */
    TOP_LEFT,
    /**
     * Horizontal alignment: center; vertical alignment: top.
     */
    TOP_CENTER,
    /**
     * Horizontal alignment: right; vertical alignment: top.
     */
    TOP_RIGHT,
    /**
     * Horizontal alignment: left; vertical alignment: center.
     */
    CENTER_LEFT,
    /**
     * Horizontal alignment: center; vertical alignment: center.
     */
    CENTER,
    /**
     * Horizontal alignment: right; vertical alignment: center.
     */
    CENTER_RIGHT,
    /**
     * Horizontal alignment: left; vertical alignment: bottom.
     */
    BOTTOM_LEFT,
    /**
     * Horizontal alignment: center; vertical alignment: bottom.
     */
    BOTTOM_CENTER,
    /**
     * Horizontal alignment: right; vertical alignment: bottom.
     */
    BOTTOM_RIGHT
}
