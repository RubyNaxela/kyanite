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

import com.rubynaxela.kyanite.math.IntRect;

/**
 * Describes a glyph in a {@link Typeface}.
 *
 * @see Typeface
 */
public record Glyph(int advance, IntRect bounds, IntRect textureRect) {

    /**
     * Constructs a glyph with the specified parameters. Note that this constructor is reserved for internal use and
     * should never be required to be used directly. Glyphs should be obtained using the {@link Typeface#getGlyph} method.
     *
     * @param advance     the offset to move horizontally to the next character
     * @param bounds      the bounding rectangle of the glyph, in coordinates relative to the baseline
     * @param textureRect the texture coordinates of the glyph on the font's texture
     */
    public Glyph {
    }
}
