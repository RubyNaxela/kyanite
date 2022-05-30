package com.rubynaxela.kyanite.core.audio;

import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;
import org.jsfml.internal.IntercomHelper;
import org.jsfml.internal.UnsafeOperations;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * Provides functionality to instantiate a {@code SoundBuffer} and play a buffered sound.
 */
@SuppressWarnings("deprecation")
public class Sound extends org.jsfml.audio.Sound {

    private ConstSoundBuffer soundBuffer = null;
    private boolean loop = false;
    private Time playingOffset = Time.ZERO;

    /**
     * Constructs an empty {@code Sound} object.
     */
    public Sound() {
    }

    /**
     * Constructs a sound with the specified {@link SoundBuffer}
     *
     * @param soundBuffer the sound buffer to use
     */
    public Sound(@NotNull ConstSoundBuffer soundBuffer) {
        setBuffer(soundBuffer);
    }

    /**
     * Constructs a sound by copying another sound.
     *
     * @param other the sound to copy
     */
    @SuppressWarnings("CopyConstructorMissesField")
    public Sound(@NotNull Sound other) {
        super(other.nativeCopy());
        UnsafeOperations.manageSFMLObject(this, true);

        final ByteBuffer buffer = IntercomHelper.getBuffer();
        nativeGetData(buffer);

        this.loop = (buffer.get(0) == 1);
        this.playingOffset = Time.us(buffer.asLongBuffer().get(1));
    }

    /**
     * Starts playing the sound or resumes it if it is currently paused.
     */
    @Override
    public void play() {
        super.play();
    }

    /**
     * Pauses the sound if it is currently playing.
     */
    @Override
    public void pause() {
        super.pause();
    }

    /**
     * Stops the sound if it is currently playing or paused.
     */
    @Override
    public void stop() {
        super.stop();
    }

    /**
     * Gets the underlying sound buffer that this sound plays from.
     *
     * @return the underlying sound buffer that this sound plays from
     */
    public ConstSoundBuffer getBuffer() {
        return soundBuffer;
    }

    /**
     * Sets the sound buffer used by this sound.
     *
     * @param soundBuffer the new sound buffer
     */
    public void setBuffer(@NotNull ConstSoundBuffer soundBuffer) {
        this.soundBuffer = Objects.requireNonNull(soundBuffer);
        nativeSetBuffer((SoundBuffer) soundBuffer);
    }

    /**
     * Returns whether or not the sound is looping.
     *
     * @return {@code true} if this sound is looping, {@code false} if not.
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Enables or disables repeated looping of the sound.
     * <p/>
     * If this is set to {@code true} and the sound has finished playing, it will
     * be restarted from the beginning as if {@code setPlayingOffset(Time.ZERO)} was called.
     *
     * @param loop {@code true} to enable looping, {@code false} to disable.
     */
    public void setLoop(boolean loop) {
        nativeSetLoop(loop);
        this.loop = loop;
    }

    /**
     * Gets the playing offset from where to start playing the underlying buffer.
     *
     * @return the playing offset from where to start playing the underlying buffer.
     */
    public Time getPlayingOffset() {
        return playingOffset;
    }

    /**
     * Sets the playing offset from where to play the underlying buffer.
     *
     * @param offset the playing offset in the underlaying buffer.
     */
    public void setPlayingOffset(@NotNull Time offset) {
        nativeSetPlayingOffset(offset.asMicroseconds());
        this.playingOffset = offset;
    }
}
