package com.rubynaxela.kyanite.core.audio;

import com.rubynaxela.kyanite.core.system.Time;
import org.jsfml.internal.Intercom;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.Objects;

/**
 * Abstract base class for streamed sound sources. Unlike buffered sounds, streamed sounds are not held in memory
 * in their entirety, but only a certain amount of sound chunks. When the currently buffered chunk is done being
 * played, the next chunk is requested from the stream's source. This approach should be preferred for long
 * sounds that do not need to be sought around in often, such as music or procedurally generated sounds.
 *
 * @see Chunk
 */
@SuppressWarnings("deprecation")
public abstract class SoundStream extends org.jsfml.audio.SoundStream {

    private int channelCount = 0;
    private int sampleRate = 0;
    private boolean loop = false;
    private Time playingOffset = Time.ZERO;

    /**
     * Starts playing the stream or resumes it if it is currently paused.
     */
    @Override
    public void play() {
        super.play();
    }

    /**
     * Pauses playback of the stream if it is currently playing.
     */
    @Override
    public void pause() {
        super.pause();
    }

    /**
     * Stops playing the stream.
     */
    @Override
    public void stop() {
        super.stop();
    }

    /**
     * Gets the amount of audio channels of this stream.
     *
     * @return the amount of audio channels of this stream
     */
    @Override
    public int getChannelCount() {
        return channelCount;
    }

    /**
     * Gets the sample rate of this stream.
     *
     * @return the sample rate of this stream in samples per second
     */
    @Override
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * Gets the playing offset at which to play from the stream.
     *
     * @return the playing offset at which to play from the stream
     */
    @Override
    public Time getPlayingOffset() {
        return playingOffset;
    }

    /**
     * Sets the current playing offset at which to play from the stream.
     *
     * @param offset the playing offset at which to play from the stream
     */
    @Override
    public final void setPlayingOffset(Time offset) {
        nativeSetPlayingOffset(offset.asMicroseconds());
        this.playingOffset = offset;
    }

    /**
     * Returns whether or not the sound stream playback is looping. If a looping sound stream has finished playing its
     * last audio chunk, it will restart playing from the first chunk as if {@code setPlayingOffset(Time.ZERO)} was invoked.
     *
     * @return {@code true} if it is looping, {@code false} if not
     */
    @Override
    public boolean isLoop() {
        return loop;
    }

    /**
     * Enables or disables repeated looping of the sound stream playback. If a looping sound stream has finished playing its
     * last audio chunk, it will restart playing from the first chunk as if {@code setPlayingOffset(Time.ZERO)} was invoked.
     *
     * @param loop {@code true} to enable looping, {@code false} to disable
     */
    @Override
    public void setLoop(boolean loop) {
        nativeSetLoop(loop);
        this.loop = loop;
    }

    @Override
    protected final void setData(int channelCount, int sampleRate) {
        this.channelCount = channelCount;
        this.sampleRate = sampleRate;
    }

    /**
     * Defines the audio stream parameters. Before the stream can be played, the implementing class must call this method.
     *
     * @param channelCount the amount of audio channels (e.g. 1 for mono, 2 for stereo)
     * @param sampleRate   the sample rate in samples per second
     */
    @Override
    protected void initialize(int channelCount, int sampleRate) {
        nativeInitialize(channelCount, sampleRate);
        setData(channelCount, sampleRate);
    }

    @Override
    @Intercom
    protected final Buffer onGetDataInternal() {
        final Chunk chunk = onGetData();
        if (chunk != null && chunk.data.length > 0) {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(4 + 2 * chunk.data.length).order(
                    ByteOrder.nativeOrder());

            int header = chunk.data.length & 0x7FFFFFFF;
            if (chunk.last) {
                header |= 0x80000000;
            }

            buffer.asIntBuffer().put(header);

            final ShortBuffer samples = buffer.asShortBuffer();
            samples.position(2);
            samples.put(chunk.data);

            return buffer;
        } else {
            return null;
        }
    }

    @Override
    @Intercom
    protected final void onSeekInternal(long time) {
        onSeek(Time.getMicroseconds(time));
    }

    /**
     * Represents a chunk of audio data provided by a {@code SoundStream} when new data is requested.
     */
    @SuppressWarnings("ClassCanBeRecord")
    public static class Chunk {

        private final short[] data;
        private final boolean last;

        /**
         * Constructs a new chunk containing the specified data.
         *
         * @param data an array of 16-bit samples representing the chunk's audio data
         * @param last determines whether this audio chunk is the last in the stream. If set to
         *             {@code true}, the stream will stop playing once this chunk has finished playing
         */
        public Chunk(short[] data, boolean last) {
            this.data = Objects.requireNonNull(data);
            this.last = last;
        }
    }
}
