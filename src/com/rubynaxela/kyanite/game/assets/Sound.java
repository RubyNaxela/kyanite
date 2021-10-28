package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.SoundSource;
import org.jsfml.system.Time;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A wrapper class for JSFML {@link org.jsfml.audio.Sound} objects, representing a sound asset.
 * The source must be the path to an audio file. Supported formats are: WAV, OGG/Vorbis and FLAC.
 */
public class Sound implements Asset {

    private final org.jsfml.audio.Sound sound;

    /**
     * Creates a new sound from the source specified by the path.
     *
     * @param path path to source audio file
     */
    public Sound(@NotNull Path path) {
        final SoundBuffer buffer = new SoundBuffer();
        try {
            buffer.loadFromFile(path);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
        sound = new org.jsfml.audio.Sound(buffer);
    }

    /**
     * Creates a new sound from the source specified by the path.
     *
     * @param path path to the source audio file
     */
    public Sound(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new sound from the source sound file.
     *
     * @param file the source audio file
     */
    public Sound(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new sound from the input stream.
     *
     * @param stream the audio data input stream
     */
    public Sound(@NotNull InputStream stream) {
        final SoundBuffer buffer = new SoundBuffer();
        try {
            buffer.loadFromStream(stream);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
        sound = new org.jsfml.audio.Sound(buffer);
    }

    /**
     * Starts playing the sound or resumes it if it is currently paused.
     */
    public void play() {
        sound.play();
    }

    /**
     * @return whether this sound is being played
     */
    public boolean isPlaying() {
        return sound.getStatus().equals(SoundSource.Status.PLAYING);
    }

    /**
     * Pauses the sound if it is currently playing.
     */
    public void pause() {
        sound.pause();
    }

    /**
     * Stops the sound if it is currently playing or paused.
     */
    public void stop() {
        sound.stop();
    }

    /**
     * Enables or disables repeated looping of the sound. If this is set to true and the sound has
     * finished playing, it will be restarted from the beginning as if setSkip(Time.ZERO) was called.
     *
     * @param looping true to enable looping, false to disable
     */
    public void setLooping(boolean looping) {
        sound.setLoop(looping);
    }

    /**
     * Sets the playing offset from where to play the underlying audio data.
     *
     * @param time the playing offset in the underlaying audio data.
     */
    public void setSkip(@NotNull Time time) {
        sound.setPlayingOffset(time);
    }

    /**
     * Sets the pitch factor of the sound. This factor is used to scale the sound's original pitch.
     *
     * @param pitchFactor the new pitch factor of the sound
     * @apiNote The default value of 1 will not affect the pitch at all. Values between
     * 0 and 1 will pitch down the sound, while values greater than 1 will pitch it up.
     */
    public void setPitch(float pitchFactor) {
        sound.setPitch(pitchFactor);
    }

    /**
     * Sets the volume of the sound. The sound volume is a percentages and ranges between 0 (silence) and 100 (full volume).
     * The default volume of a sound is 100.
     *
     * @param volumeFactor the new volume of the sound, ranging between 0 and 100.
     */
    public void setVolume(float volumeFactor) {
        sound.setVolume(volumeFactor);
    }
}
