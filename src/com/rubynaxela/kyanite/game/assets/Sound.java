package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.core.audio.SoundBuffer;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.util.MathUtils;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.core.audio.SoundSource.Status;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A wrapper class for JSFML {@link com.rubynaxela.kyanite.core.audio.Sound} objects, representing a sound asset. The source
 * must be the path or an {@link InputStream} to an audio file. Supported formats are: WAV, OGG/Vorbis and FLAC.
 */
public class Sound implements Asset {

    private static final AudioHandler handler = GameContext.getInstance().getAudioHandler();
    final com.rubynaxela.kyanite.core.audio.Sound sound;
    float volumeFactor, pitchFactor;

    /**
     * Creates a new sound from the source specified by the path.
     *
     * @param path path to source audio file
     */
    public Sound(@NotNull Path path) {
        sound = new com.rubynaxela.kyanite.core.audio.Sound(load(path));
        volumeFactor = sound.getVolume();
        pitchFactor = sound.getPitch();
        setVolume(volumeFactor);
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
        sound = new com.rubynaxela.kyanite.core.audio.Sound(load(stream));
        volumeFactor = sound.getVolume();
        pitchFactor = sound.getPitch();
        setVolume(volumeFactor);
    }

    private <T> SoundBuffer load(@NotNull T source) {
        final SoundBuffer buffer = new SoundBuffer();
        if (source instanceof final Path path) buffer.loadFromFile(path);
        else if (source instanceof final InputStream stream) buffer.loadFromStream(stream);
        return buffer;
    }

    /**
     * Starts playing the sound or resumes it if it is currently paused. Volume
     * of the sound depends on the {@link AudioHandler}'s master volume setting.
     */
    public void play() {
        sound.play();
    }

    /**
     * @return whether this sound is being played
     */
    public boolean isPlaying() {
        return sound.getStatus().equals(Status.PLAYING);
    }

    /**
     * Pauses the sound if it is currently playing.
     */
    public void pause() {
        sound.pause();
    }

    /**
     * @return whether this sound is currently paused
     */
    public boolean isPaused() {
        return sound.getStatus().equals(Status.PAUSED);
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
     * @param time the playing offset in the underlaying audio data
     */
    public void setSkip(@NotNull Time time) {
        sound.setPlayingOffset(time);
    }

    /**
     * Gets the sound's current pitch factor. A value of 1 means that the sound is not pitched, a value between
     * 0 and 1 means that the sound is pitched down, a value greater than 1 means that the sound is pitched up.
     *
     * @return the sound's current pitch factor
     */
    public float getPitch() {
        return pitchFactor;
    }

    /**
     * Sets the pitch factor of the sound. This factor is used to scale the sound's original
     * pitch. The default value of 1 will not affect the pitch at all. Values between
     * 0 and 1 will pitch down the sound, while values greater than 1 will pitch it up.
     *
     * @param pitchFactor the new pitch factor of the sound
     */
    public void setPitch(float pitchFactor) {
        sound.setPitch(pitchFactor);
        this.pitchFactor = pitchFactor;
    }

    /**
     * Gets the sound's current volume. The volume level ranges between 0 (silence) and 100 (full volume).
     *
     * @return the sound's current volume, ranging between 0 (silence) and 100 (full volume)
     */
    public float getVolume() {
        return volumeFactor;
    }

    /**
     * Sets the volume of the sound. The sound volume is a percentage and ranges
     * between 0 (silence) and 100 (full volume). The default volume of a sound is 100.
     *
     * @param volumeFactor the new volume of the sound, ranging between 0 and 100
     */
    public void setVolume(float volumeFactor) {
        volumeFactor = MathUtils.clamp(volumeFactor, 0f, 100f);
        sound.setVolume(handler.masterVolume * volumeFactor / 100f);
        this.volumeFactor = volumeFactor;
    }

    com.rubynaxela.kyanite.core.audio.Sound raw() {
        return sound;
    }
}
