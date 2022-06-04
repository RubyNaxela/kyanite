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

package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.audio.SoundSource.Status;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.util.AssetId;
import com.rubynaxela.kyanite.math.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A sounds management utility class. Provides functionality for stopping or pausing/resuming all sounds,
 * setting master volume and playing sounds in separate abstract channels that can have individual
 * volume settings, as well as collective stopping or pausing/resuming sounds from specific channels
 */
public class AudioHandler {

    final List<Sound> globalSounds = new ArrayList<>();
    private final GameContext context;
    private final Map<String, Channel> channels = new TreeMap<>();
    private final Object lock = new Object();
    float masterVolume = 100f;

    /**
     * Creates the {@code AudioHandler} for the game. It is created automatically before
     * the pre-init stage of the game and there is no need to create one manually.
     *
     * @param context the {@code GameContext} instance
     */
    public AudioHandler(@NotNull GameContext context) {
        this.context = context;
    }

    /**
     * Pauses all sounds that were playing. They can be later resumed
     * with the {@link AudioHandler#resumeAllPausedSounds} method.
     */
    public void pauseAllPlayingSounds() {
        globalSounds.stream().filter(Sound::isPlaying).forEach(Sound::pause);
        channels.forEach((n, channel) -> channel.sounds.forEach(com.rubynaxela.kyanite.audio.Sound::pause));
    }

    /**
     * Pauses all sounds that were playing in the specified channel. They can
     * be later resumed with the {@link AudioHandler#resumeAllPausedSounds} method.
     *
     * @param channelName the channel to pause
     */
    public void pauseAllPlayingSounds(@NotNull String channelName) {
        try {
            channels.get(channelName).sounds.forEach(com.rubynaxela.kyanite.audio.Sound::pause);
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
        channels.forEach((n, channel) -> channel.sounds.forEach(com.rubynaxela.kyanite.audio.Sound::play));
    }

    /**
     * Resumes all sounds in the specified channel.
     *
     * @param channelName the channel to resume
     */
    public void resumeAllPausedSounds(@NotNull String channelName) {
        try {
            channels.get(channelName).sounds.forEach(com.rubynaxela.kyanite.audio.Sound::play);
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
            channel.sounds.forEach(com.rubynaxela.kyanite.audio.Sound::stop);
            channel.sounds.clear();
        });
    }

    /**
     * Stops all sounds that were playing in the specified channel.
     *
     * @param channelName the channel to stop
     */
    public void stopAllSounds(@NotNull String channelName) {
        try {
            final Channel channel = channels.get(channelName);
            channel.sounds.forEach(com.rubynaxela.kyanite.audio.Sound::stop);
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

    /**
     * Creates a new abstract audio channel through which sounds can be played, as well as collectively paused and stopped
     *
     * @param name the channel name (ID)
     */
    public void createChannel(@NotNull String name) {
        channels.put(name, new Channel());
    }

    /**
     * Gets the current volume of the specified channel, ranging from 0 (silence) to 100 (full volume).
     *
     * @param channelName the channel name
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
     * @param channelName the channel for which to set the volume
     * @param volume      the new master volume, ranging between 0 and 100
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
     * @param looping     {@code true} to play the specified sound in a loop; {@code false} to play once
     * @return a reference to the raw sound being played, so it can be stored to control later
     */
    public com.rubynaxela.kyanite.audio.Sound playSound(@NotNull Sound source, @NotNull String channelName,
                                                        float volume, float pitch, boolean looping) {
        if (context.getWindow() == null)
            throw new IllegalStateException("The playSound method cannot be called before the game window is initialized");
        try {
            final Channel channel = channels.get(channelName);
            final com.rubynaxela.kyanite.audio.Sound sound = new com.rubynaxela.kyanite.audio.Sound(source.raw().getBuffer());
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
     * @param looping     {@code true} to play the specified sound in a loop; {@code false} to play once
     * @return a reference to the raw sound being played, so it can be stored to control later
     */
    public com.rubynaxela.kyanite.audio.Sound playSound(@NotNull @AssetId String id, @NotNull String channelName,
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

        List<com.rubynaxela.kyanite.audio.Sound> sounds = new LinkedList<>();
        float volume = 100f;
    }
}
