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
 * Provides font style constants. These constants can be combined
 * using an arithmetic {@code OR} operation to define a font style.
 */
public interface FontStyle {

    /**
     * Regular drawing style.
     */
    int REGULAR = 0;
    /**
     * Bold drawing style.
     */
    int BOLD = 0x01;
    /**
     * Italic drawing style.
     */
    int ITALIC = 0x02;
    /**
     * Underlined drawing style.
     */
    int UNDERLINED = 0x04;
}
