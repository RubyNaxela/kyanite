package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.util.AssetId;
import com.rubynaxela.kyanite.util.MathUtils;
import org.jetbrains.annotations.NotNull;
import org.jsfml.audio.SoundSource.Status;

import java.util.*;

/**
 * A sounds management utility class. Provides functionality for stopping or pausing/resuming all sounds, setting
 * master volume and playing sounds in separate abstract channels that can have individual volume settings.
 */
public class AudioHandler {

    final List<Sound> globalSounds = new ArrayList<>();
    private final GameContext context;
    private final Map<String, Channel> channels = new TreeMap<>();
    private final Object lock = new Object();
    float masterVolume = 100f;

    public AudioHandler(@NotNull GameContext context) {
        this.context = context;
    }

    /**
     * Pauses all sounds that were playing. They can be later resumed
     * with the {@link AudioHandler#resumeAllPausedSounds} method.
     */
    public void pauseAllPlayingSounds() {
        globalSounds.stream().filter(Sound::isPlaying).forEach(Sound::pause);
        channels.forEach((n, channel) -> channel.sounds.forEach(org.jsfml.audio.Sound::pause));
    }

    /**
     * Pauses all sounds that were playing in the specified channel. They can
     * be later resumed with the {@link AudioHandler#resumeAllPausedSounds} method.
     */
    public void pauseAllPlayingSounds(@NotNull String channelName) {
        try {
            channels.get(channelName).sounds.forEach(org.jsfml.audio.Sound::pause);
        } catch (NullPointerException ignored) {
            throw new NullPointerException("Channel " + channelName + " either does not exist " +
                                           "or was attempted to be used before being created");
        }
    }

    /**
     * Resumes all sounds that had been paused (not least with the {@link AudioHandler#pauseAllPlayingSounds} method).
     */
    public void resumeAllPausedSounds() {
        globalSounds.stream().filter(Sound::isPaused).forEach(Sound::play);
        channels.forEach((n, channel) -> channel.sounds.forEach(org.jsfml.audio.Sound::play));
    }

    /**
     * Resumes all sounds in the specified channel.
     */
    public void resumeAllPausedSounds(@NotNull String channelName) {
        try {
            channels.get(channelName).sounds.forEach(org.jsfml.audio.Sound::play);
        } catch (NullPointerException ignored) {
            throw new NullPointerException("Channel " + channelName + " either does not exist " +
                                           "or was attempted to be used before being created");
        }
    }

    /**
     * Stops all sounds that were playing.
     */
    public void stopAllSounds() {
        globalSounds.forEach(Sound::stop);
        channels.forEach((n, channel) -> {
            channel.sounds.forEach(org.jsfml.audio.Sound::stop);
            channel.sounds.clear();
        });
    }

    /**
     * Stops all sounds that were playing in the specified channel.
     */
    public void stopAllSounds(@NotNull String channelName) {
        try {
            final Channel channel = channels.get(channelName);
            channel.sounds.forEach(org.jsfml.audio.Sound::stop);
            channel.sounds.clear();
        } catch (NullPointerException ignored) {
            throw new NullPointerException("Channel " + channelName + " either does not exist " +
                                           "or was attempted to be used before being created");
        }
    }

    /**
     * @return current master volume setting
     */
    public float getMasterVolume() {
        return masterVolume;
    }

    /**
     * Sets the global volume factor which is multiplied by the volume factors of the individual sounds. The volume
     * is a percentage and ranges between 0 (silence) and 100 (full volume). The initial master volume is 100.
     *
     * @param volume the new master volume, ranging between 0 and 100
     */
    public void setMasterVolume(float volume) {
        masterVolume = MathUtils.clamp(volume, 0f, 100f);
        globalSounds.forEach(s -> s.sound.setVolume(masterVolume * s.volumeFactor / 100f));
    }

    public void createChannel(@NotNull String name) {
        channels.put(name, new Channel());
    }

    /**
     * @return current volume setting for the specified channel
     * @throws NullPointerException if channel of the specified name does not exist
     */
    public float getChannelVolume(@NotNull String channelName) {
        try {
            return channels.get(channelName).volume;
        } catch (NullPointerException ignored) {
            throw new NullPointerException("Channel " + channelName + " either does not exist " +
                                           "or was attempted to be used before being created");
        }
    }

    /**
     * Sets the volume factor for the specified channel, which is multiplied by the
     * volume factors of the individual sounds. The volume is a percentage and ranges
     * between 0 (silence) and 100 (full volume). The default channel volume is 100.
     *
     * @param volume the new master volume, ranging between 0 and 100
     * @throws NullPointerException if channel of the specified name does not exist
     */
    public void setChannelVolume(@NotNull String channelName, float volume) {
        try {
            channels.get(channelName).volume = MathUtils.clamp(volume, 0f, 100f);
        } catch (NullPointerException ignored) {
            throw new NullPointerException("Channel " + channelName + " either does not exist " +
                                           "or was attempted to be used before being created");
        }
    }

    /**
     * Plays a sound in the specified channel. Each time this method is called, a copy of the
     * sound is created using the specified {@link Sound} object's buffer, thus each call may have
     * a different volume and pitch setting without affecting the specified object's settings.
     *
     * @param source      a {@link Sound} object
     * @param channelName the name of the channel
     * @param volume      the volume of the sound, ranging between 0 (silence) and 100 (full volume)
     * @param pitch       the pitch of the sound (values between 0 and 1 will pitch
     *                    down the sound; values greater than 1 will pitch it up)
     * @return reference to the new sound object
     */
    public org.jsfml.audio.Sound playSound(@NotNull Sound source, @NotNull String channelName,
                                           float volume, float pitch, boolean looping) {
        if (context.getWindow() == null)
            throw new IllegalStateException("The playSound method cannot be called before the game window is initialized");
        try {
            final Channel channel = channels.get(channelName);
            final org.jsfml.audio.Sound sound = new org.jsfml.audio.Sound(source.raw().getBuffer());
            sound.setVolume(volume * channel.volume / 100f);
            sound.setPitch(pitch);
            sound.play();
            sound.setLoop(looping);
            channel.sounds.add(sound);
            return sound;
        } catch (NullPointerException ignored) {
            throw new NullPointerException("Channel " + channelName + " either does not exist " +
                                           "or was attempted to be used before being created");
        }
    }

    /**
     * Plays a sound in the specified channel. Each time this method is called, a copy of the sound
     * is created using buffer of the {@link Sound} of the specified ID, thus each call may have
     * a different volume and pitch setting without affecting that {@code Sound} object's settings.
     *
     * @param id          ID of a {@link Sound} object from the assets bundle
     * @param channelName the name of the channel
     * @param volume      the volume of the sound, ranging between 0 (silence) and 100 (full volume)
     * @param pitch       the pitch of the sound (values between 0 and 1 will pitch
     *                    down the sound; values greater than 1 will pitch it up)
     */
    public org.jsfml.audio.Sound playSound(@NotNull @AssetId String id, @NotNull String channelName,
                                           float volume, float pitch, boolean looping) {
        try {
            return playSound((Sound) context.getAssetsBundle().get(id), channelName, volume, pitch, looping);
        } catch (NullPointerException | ClassCastException ignored) {
            throw new NullPointerException("Sound of ID " + id + " either does not exist or " +
                                           "was attempted to be used before being registered");
        }
    }

    /**
     * Garbage collection. This method clears all the sounds that have
     * stopped playing. It is automatically called by the window loop.
     */
    public void gc() {
        channels.forEach((n, ch) -> ch.sounds.removeIf(s -> s.getStatus() == Status.STOPPED));
    }

    private static class Channel {

        List<org.jsfml.audio.Sound> sounds = new LinkedList<>();
        float volume = 100f;
    }
}
