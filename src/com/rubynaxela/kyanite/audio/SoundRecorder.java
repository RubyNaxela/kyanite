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

import com.rubynaxela.kyanite.core.SFMLNative;

/**
 * Abstract base class for sound recorders, which provide functionality to capture audio data.
 */
@SuppressWarnings("deprecation")
public abstract class SoundRecorder extends org.jsfml.audio.SoundRecorder {

    static {
        SFMLNative.loadNativeLibraries();
    }

    /**
     * Constructs a sound recorder.
     */
    protected SoundRecorder() {
        super();
    }

    /**
     * Checks whether audio capturing is available on this system.
     *
     * @return {@code true} if audio capturing is available, {@code false} otherwise
     */
    public static boolean isAvailable() {
        return org.jsfml.audio.SoundRecorder.isAvailable();
    }

    /**
     * Starts capturing audio data with a sample rate of 44,100 Hz.
     */
    public final void start() {
        start(44100);
    }

    /**
     * Starts capturing audio data with the specified sample rate.
     *
     * @param sampleRate the sample rate in samples per second
     */
    public final void start(int sampleRate) {
        super.start(sampleRate);
    }

    /**
     * Stops capturing audio data.
     */
    public final void stop() {
        super.stop();
    }

    /**
     * Gets the sample rate that audio is being captured with.
     *
     * @return the audio sample rate in samples per second
     */
    public final int getSampleRate() {
        return super.getSampleRate();
    }

    /**
     * Called when the sound recorder starts recording. This method can be implemented by
     * deriving classes to perform any actions necessary before audio recording actually starts.
     *
     * @return {@code true} to start recording after this method is done, {@code false} to cancel
     */
    @Override
    protected abstract boolean onStart();

    /**
     * Called when a new batch of audio samples comes in. Implementing classes can then process
     * the captured audio data. Note that this method will be called in a separate audio
     * capturing thread. Also note that this method is hardcoded to be called every 100ms.
     *
     * @param samples the 16-bit mono samples that were captured
     * @return {@code true} to continue recording after this method is done, {@code false} to stop recording
     */
    @Override
    protected abstract boolean onProcessSamples(short[] samples);

    /**
     * Called when the audio capture has stopped. Note that this method will be called in a separate audio capturing thread.
     */
    @Override
    protected abstract void onStop();
}
