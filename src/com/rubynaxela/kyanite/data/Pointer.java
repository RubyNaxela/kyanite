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

package com.rubynaxela.kyanite.data;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * This class introduces a readable syntax for creating and working with
 * pointers in Java. The most characteristic use cases for pointers are:
 * <ul>
 *     <li>
 *         in anonymous class methods (and lambda expressions), for instance:<pre>
 * final Pointer&lt;Integer> sum = new Pointer&lt;>(0);
 * iterator.forEachRemaining(v -> sum.value++);</pre>
 *     </li>
 *     <li>for references late-initialized from places other than the reference holder</li>
 * </ul>
 *
 * @param <T> the pointer type
 */
public class Pointer<T> {

    /**
     * The value referenced by this pointer.
     */
    public T value;

    /**
     * Creates a new {@code Pointer} object referencing the specified value.
     *
     * @param value the value to be referenced by this object
     */
    public Pointer(@Nullable T value) {
        this.value = value;
    }

    /**
     * Returns a new pointer to {@code null} address
     *
     * @param <T> the pointer type
     * @return a null pointer
     */
    public static <T> Pointer<T> nullptr() {
        return new Pointer<>(null);
    }

    /**
     * Checks whether this pointer and the specified pointer point to the same address. Note
     * that this method will return {@code false} when the pointers hold two distinct objects,
     * even if calling {@link Objects#equals} on these objects would return {@code true}.
     *
     * @param other other {@code Pointer}
     * @return whether this pointer and the specified pointer point to the same address
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof final Pointer<?> pointer)) return false;
        return value == pointer.value;
    }
}

