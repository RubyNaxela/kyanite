package com.rubynaxela.kyanite.core.audio;

import com.rubynaxela.kyanite.math.Vector3f;
import org.jsfml.internal.IntercomHelper;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Abstract base class for playable sound sources.
 */
@SuppressWarnings("deprecation")
public abstract class SoundSource extends org.jsfml.audio.SoundSource {

    private float volume = 100, pitch = 1, minDistance = 1, attenuation = 1;
    private Vector3f position = Vector3f.ZERO;
    private boolean relativeToListener = false;

    /**
     * Constructs a {@code SoundSource} object.
     */
    public SoundSource() {
    }

    @Deprecated
    protected SoundSource(long wrap) {
        super(wrap);

        final ByteBuffer buffer = IntercomHelper.getBuffer();
        final FloatBuffer floats = IntercomHelper.getBuffer().asFloatBuffer();

        nativeGetData(buffer);
        this.relativeToListener = (buffer.get(0) == 1);
        this.volume = floats.get(1);
        this.pitch = floats.get(2);
        this.position = new Vector3f(floats.get(3), floats.get(4), floats.get(5));
        this.minDistance = floats.get(6);
        this.attenuation = floats.get(7);
    }

    /**
     * Gets the sound's current pitch factor. A value of 1 means that the sound is not pitched, a value between
     * 0 and 1 means that the sound is pitched down, a value greater than 1 means that the sound is pitched up.
     *
     * @return the sound's current pitch factor
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Sets the pitch factor of the sound. This factor is used to scale the sound's original
     * pitch. This means that the default value of 1 will not affect the pitch at all. Values
     * between 0 and 1 will pitch down the sound, while values greater than 1 will pitch it up.
     *
     * @param pitch the new pitch factor of the sound
     */
    public void setPitch(float pitch) {
        nativeSetPitch(pitch);
        this.pitch = pitch;
    }

    /**
     * Gets the sound's current volume. The volume level ranges between 0 (silence) and 100 (full volume).
     *
     * @return the sound's current volume, ranging between 0 (silence) and 100 (full volume)
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the sound. The sound volume is a percentages and ranges
     * between 0 (silence) and 100 (full volume). The default volume of a sound is 100.
     *
     * @param volume the new volume of the sound, ranging between 0 and 100
     */
    public void setVolume(float volume) {
        nativeSetVolume(volume);
        this.volume = volume;
    }

    /**
     * Gets the sound's current position in the scene.
     *
     * @return the sound's current position in the scene
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Sets the position of the sound in the scene. This allows for sound spatialization, also involving the
     * {@link Listener}. The sound position is set either absolutely in the scene or relatively to the {@code Listener},
     * depending on whether {@link #isRelativeToListener()} is {@code true} or {@code false}. By default, a sound is
     * located at the origin <i>{@code (0, 0, 0)}</i>. Note that only mono sounds (ie 1 audio channel) can be spatialized.
     *
     * @param position the sound's new position
     * @see #setRelativeToListener(boolean)
     */
    public void setPosition(Vector3f position) {
        nativeSetPosition(position.x, position.y, position.z);
        this.position = position;
    }

    /**
     * Sets the position of the sound in the scene. This allows for sound spatialization, also involving the
     * {@link Listener}. The sound position is set either absolutely in the scene or relatively to the {@code Listener},
     * depending on whether {@link #isRelativeToListener()} is {@code true} or {@code false}. By default, a sound is
     * located at the origin <i>{@code (0, 0, 0)}</i>. Note that only mono sounds (i.e. 1 audio channel) can be spatialized.
     *
     * @param x the sound's new X coordinate
     * @param y the sound's new Y coordinate
     * @param z the sound's new Z coordinate
     * @see #setRelativeToListener(boolean)
     */
    public final void setPosition(float x, float y, float z) {
        setPosition(new Vector3f(x, y, z));
    }

    /**
     * Returns whether the sound's position is relative to the {@link Listener}.
     *
     * @return {@code true} if the sound's position is relative to the listener, {@code false} if it is absolute
     * @see SoundSource#setRelativeToListener(boolean)
     */
    public boolean isRelativeToListener() {
        return relativeToListener;
    }

    /**
     * Determines whether the sound position is bound to be relative to the {@link Listener} or whether
     * it is positioned absolutely in the scene. By default, the sound's position in the scene is absolute.
     *
     * @param relative {@code true} to make the sound position relative to the listener, {@code false} to make it absolute
     * @see SoundSource#setPosition(float, float, float)
     */
    public void setRelativeToListener(boolean relative) {
        nativeSetRelativeToListener(relative);
        this.relativeToListener = relative;
    }

    /**
     * Gets the sound's minimum distance from the {@link com.rubynaxela.kyanite.core.audio.Listener} before attenuation sets in.
     *
     * @return the sound's minimum distance before attenuation sets in
     * @see SoundSource#setMinDistance(float)
     */
    public float getMinDistance() {
        return minDistance;
    }

    /**
     * Sets the minimum distance of the sound before attenuation kicks in. If the distance
     * between the sound and the {@link Listener} is less or equal to this value, the sound will be
     * heard at its maximum volume. As the distance becomes larger, the sound is attenuated (i.e.
     * become more quiet) according to its attenuation factor. The default minimum distance is 1.
     *
     * @param distance the minimum distance before attenuation in world units
     * @see SoundSource#setAttenuation(float)
     */
    public void setMinDistance(float distance) {
        nativeSetMinDistance(distance);
        this.minDistance = distance;
    }

    /**
     * Gets the sound's attenuation factor. The attenuation factor ranges between 0
     * (no attenuation) and 100 (instant attenuation), where the default value is 1.
     *
     * @return the sound's attenuation factor.
     * @see SoundSource#setAttenuation(float)
     */
    public float getAttenuation() {
        return attenuation;
    }

    /**
     * Sets the sound's attenuation factor. As the distance between the sound and the {@link Listener} becomes
     * higher than the minimum distance, the sound volume will decrease according to this factor. The attenuation
     * factor ranges between 0 (no attenuation) and 100 (instant attenuation), where the default value is 1.
     *
     * @param att the new attenuation factor, ranging between 0 (no attenuation) and 100 (instant attenuation)
     * @see SoundSource#setMinDistance(float)
     */
    public void setAttenuation(float att) {
        nativeSetAttenuation(att);
        this.attenuation = att;
    }

    /**
     * Gets the current state of the sound stream.
     *
     * @return the current state of the sound stream
     */
    protected Status getStatus() {
        return Status.values()[nativeGetStatus()];
    }

    /**
     * Enumeration of possible sound source states.
     */
    public enum Status {
        /**
         * Indicates that the sound is currently stopped or has finished playing.
         */
        STOPPED,
        /**
         * Indicates that the sound is currently paused and can be resumed.
         */
        PAUSED,
        /**
         * Indicates that the sound is currently playing.
         */
        PLAYING
    }
}
