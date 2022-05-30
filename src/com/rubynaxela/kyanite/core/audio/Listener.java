package com.rubynaxela.kyanite.core.audio;

import com.rubynaxela.kyanite.math.Vector3f;
import org.jetbrains.annotations.NotNull;
import org.jsfml.internal.SFMLNative;

/**
 * Represents the point in the scene from where all the sounds are heard and provides funcionality to modify it. Modifying
 * the position and orientation ("view direction") of the listener changes the way sounds are heard to accomplish 3D sound.
 * Sounds will be panned and attenuated according to their position relative to the listener and the listener's orientation.
 */
@SuppressWarnings("deprecation")
public final class Listener extends org.jsfml.audio.Listener {

    private static float volume = 100;
    private static Vector3f position = Vector3f.ZERO;
    private static Vector3f direction = Vector3f.ZERO;

    static {
        SFMLNative.loadNativeLibraries();
    }

    private Listener() {
    }

    /**
     * Gets the global sound volume.
     *
     * @return the global sound volume, ranging between 0 (silence) and 100 (full volume)
     */
    public static float getGlobalVolume() {
        return volume;
    }

    /**
     * Sets the global sound volume. The default global volume is 100 (maximum).
     *
     * @param volume the global sound volume, ranging between 0 (silence) and 100 (full volume)
     */
    public static void setGlobalVolume(float volume) {
        nativeSetGlobalVolume(volume);
        Listener.volume = volume;
    }

    /**
     * Gets the listener's current position in the scene.
     *
     * @return the listener's current position in the scene
     */
    public static Vector3f getPosition() {
        return position;
    }

    /**
     * Sets the position of the listener in the scene. Initially, the listener is located at the origin <i>(0, 0, 0)</i>.
     *
     * @param position the listener's new position
     * @see SoundSource#setPosition(Vector3f)
     */
    public static void setPosition(@NotNull Vector3f position) {
        nativeSetPosition(position.x, position.y, position.z);
        Listener.position = position;
    }

    /**
     * Sets the position of the listener in the scene. Initially, the listener is located at the origin <i>(0, 0, 0)</i>.
     *
     * @param x the X coordinate of the listener's new position
     * @param y the Y coordinate of the listener's new position
     * @param z the Z coordinate of the listener's new position
     * @see SoundSource#setPosition(Vector3f)
     */
    public static void setPosition(float x, float y, float z) {
        setPosition(new Vector3f(x, y, z));
    }

    /**
     * Gets the listener's current orientation or "view direction" in the scene.
     *
     * @return the listener's current orientation in the scene
     */
    public static Vector3f getDirection() {
        return direction;
    }

    /**
     * Sets the orientation or "view direction" of the listener in the scene.
     * <p/>
     * The vector passed does not need to be normalized. Initially, the listener's orientation
     * is along the Z axis, looking "into" the screen <i>(0, 0, -1)</i>.
     *
     * @param direction the listener's new orientation.
     * @see SoundSource#setPosition(Vector3f)
     */
    public static void setDirection(@NotNull Vector3f direction) {
        nativeSetDirection(direction.x, direction.y, direction.z);
        Listener.direction = direction;
    }

    /**
     * Sets the orientation or "view direction" of the listener in the scene. The vector passed does not need to be
     * normalized. Initially, the listener's orientation is along the Z axis, looking "into" the screen <i>(0, 0, -1)</i>.
     *
     * @param x the X component of the listener's new orientation
     * @param y the Y component of the listener's new orientation
     * @param z the Z component of the listener's new orientation
     * @see SoundSource#setPosition(Vector3f)
     */
    public static void setDirection(float x, float y, float z) {
        setDirection(new Vector3f(x, y, z));
    }
}
