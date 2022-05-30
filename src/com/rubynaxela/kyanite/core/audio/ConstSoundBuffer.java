package com.rubynaxela.kyanite.core.audio;

import com.rubynaxela.kyanite.util.Time;
import org.jsfml.internal.Const;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Read-only interface for sound buffers. It provides methods to can gain information from a sound
 * buffer (such as the duration) and save it to a file. Note that this interface is expected to be
 * implemented by a {@link SoundBuffer}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstSoundBuffer extends Const {

    /**
     * Attempts to save the sound buffer to a file.
     *
     * @param path the path to the file to write
     * @throws IOException in case saving failed
     */
    void saveToFile(Path path) throws IOException;

    /**
     * Retrieves the raw 16-bit audio samples stored in the buffer.
     *
     * @return the raw audio 16-bit samples stored in the buffer
     */
    short[] getSamples();

    /**
     * Retrieves the amount of samples stored in the buffer.
     *
     * @return the amount of samples stored in the buffer
     */
    int getSampleCount();

    /**
     * Gets the sound buffer's sample rate in samples per second.
     *
     * @return the sound buffer's sample rate in samples per second
     */
    int getSampleRate();

    /**
     * Gets the amount of audio channels in the buffer (e.g. 1 for mono, 2 for stereo etc).
     *
     * @return the amount of audio channels in the buffer
     */
    int getChannelCount();

    /**
     * Gets the duration of the sound.
     *
     * @return the duration of the sound
     */
    Time getDuration();
}
