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

package com.rubynaxela.kyanite.window;

import com.rubynaxela.kyanite.core.SFMLNative;

/**
 * Holds a valid OpenGL drawing context. For every OpenGL call, a valid context is required.
 * Using this class, by creating an instance, you can obtain a valid context. This is
 * only required if you do not have an active window that provides an OpenGL context.
 */
@SuppressWarnings("deprecation")
public final class Context extends org.jsfml.window.Context {

    private static final ThreadLocal<Context> threadContext = new ThreadLocal<>();

    private Context() {
        SFMLNative.ensureDisplay();
    }

    /**
     * Creates and activates a valid OpenGL context on the current thread. If there
     * already is a context in the current thread, it will simply be activated.
     *
     * @return the activated OpenGL context
     */
    public static Context getContext() {
        Context context = threadContext.get();
        if (context != null) {
            try {
                context.setActive(true);
            } catch (ContextActivationException ignored) {
            }
        } else {
            context = new Context();
            threadContext.set(context);
        }
        return context;
    }

    /**
     * Explictly activates or deactivates the OpenGL context.
     *
     * @param active {@code true} to activate, {@code false} to deactivate
     * @throws ContextActivationException if activating or deactivating the context failed
     */
    public void setActive(boolean active) throws ContextActivationException {
        if (!nativeSetActive(active))
            throw new ContextActivationException("Failed to " + (active ? "" : "de") + "activate the context.");
    }
}
