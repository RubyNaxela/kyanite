/*
 * Copyright (c) 2022 Alex Pawelski
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

package com.rubynaxela.kyanite.system;

import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for read-only clocks. Provides methods to gain measured time and information
 * about the clock, but not to control it. Note that this interface is expected to be implemented
 * by a {@link Clock}. It is not recommended to be implemented outside the Kyanite API.
 */
public interface ConstClock {

    /**
     * Returns {@code true} if this clock has been started. This method returns {@code true} even if this clock is paused.
     *
     * @return {@code true} if this clock has been started, {@code false} otherwise
     * @see #isPaused for determining if the clock is currently paused
     */
    boolean isActive();

    /**
     * Returns {@code true} if this clock is currently paused.
     *
     * @return {@code true} if this clock is currently paused, {@code false} otherwise
     */
    boolean isPaused();

    /**
     * Gets the total measured time (the total time the clock was active and not paused).
     *
     * @return the total measured time
     */
    @NotNull Time getTime();
}
