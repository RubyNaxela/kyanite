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

package com.rubynaxela.kyanite.audio;

import com.rubynaxela.kyanite.util.NativeImplementation;

/**
 * A {@code SoundRecorder} which stores captured audio data into a {@code SoundBuffer}.
 */
@SuppressWarnings("deprecation")
public class SoundBufferRecorder extends org.jsfml.audio.SoundBufferRecorder {

    private final SoundBuffer soundBuffer;

    /**
     * Constructs a sound buffer recorder.
     */
    public SoundBufferRecorder() {
        soundBuffer = new SoundBuffer(nativeGetBuffer());
    }

    /**
     * Gets the sound buffer containing the captured audio data. The sound buffer will remain empty until
     * any sound has been successfully captured using the {@link #start(int)} and {@link #stop()} methods.
     *
     * @return the sound buffer containing the captured audio data
     */
    public ConstSoundBuffer getBuffer() {
        return soundBuffer;
    }

    @NativeImplementation
    @Override
    protected final boolean onStart() {
        return true;
    }

    @NativeImplementation
    @Override
    protected final boolean onProcessSamples(short[] samples) {
        return true;
    }

    @NativeImplementation
    @Override
    protected final void onStop() {
    }
}
