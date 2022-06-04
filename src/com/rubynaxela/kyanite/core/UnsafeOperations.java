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

/**
 * Provides inherently unsafe operations on native SFML objects. These need to be public in order to
 * maintain Kyanite's package structure, but should by no means used outside of the Kyanite framework.
 */
public final class UnsafeOperations {

    private UnsafeOperations() {
    }

    /**
     * Flags an SFML object as Java managed or unmanaged. Java managed objects will be destroyed using the
     * {@code SFMLNativeObject#nativeDelete()} method when this object gets finalized. This is used for
     * Kyanite to differentiate between explicitly self-constructed SFML objects (using {@code new}) and
     * SFML objects that are managed by other SFML objects, but require a Java representation. Wrong use of
     * this method will make the application prone to crashes and memory leaks, so handle with extreme care.
     *
     * @param object  the SFML object wrapper
     * @param managed whether or not this object is managed by Kyanite
     */
    public static void manageSFMLObject(SFMLNativeObject object, boolean managed) {
        object.setJavaManaged(managed);
    }
}
