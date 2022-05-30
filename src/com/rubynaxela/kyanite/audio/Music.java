package com.rubynaxela.kyanite.audio;

import com.rubynaxela.kyanite.util.NativeImplementation;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.system.IOException;
import org.jsfml.internal.IntercomHelper;
import org.jsfml.internal.SFMLErrorCapture;
import org.jsfml.internal.SFMLInputStream;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Provides functionality to play music streams from common audio file formats. Audio files can be opened using either the
 * {@link #openFromFile(Path)} or {@link #openFromStream(InputStream)} methods. The supported audio file formats are: {@code
 * ogg, wav, flac, aiff, au, raw, paf, svx, nist, voc, ircam, w64, mat4, mat5 pvf, htk, sds, avr, sd2, caf, wve, mpc2k, rf64}.
 */
@SuppressWarnings("deprecation")
public class Music extends org.jsfml.audio.Music {

    private final SFMLInputStream.NativeStreamRef streamRef = new SFMLInputStream.NativeStreamRef();
    private Time duration = Time.ZERO;

    /**
     * Constructs a {@code Music} object.
     */
    public Music() {
        super();
    }

    @NativeImplementation
    @Override
    protected final void initialize(int channelCount, int sampleRate) {
    }

    @NativeImplementation
    @Override
    protected final SoundStream.Chunk onGetData() {
        return null;
    }

    @NativeImplementation
    @Override
    protected final void onSeek(Time time) {
    }

    /**
     * Attempts to open the music from an {@code InputStream}.
     *
     * @param in the input stream to stream from
     * @throws IOException in case an I/O error occurs
     */
    public void openFromStream(InputStream in) throws IOException {
        streamRef.initialize(new SFMLInputStream(Objects.requireNonNull(in)));

        SFMLErrorCapture.start();
        final boolean success = nativeOpenFromStream(streamRef);
        final String msg = SFMLErrorCapture.finish();

        if (!success) throw new IOException(msg);

        sync();
    }

    /**
     * Attempts to open the music from a file.
     *
     * @param path the file to stream from
     * @throws IOException in case an I/O error occurs
     */
    public void openFromFile(Path path) throws IOException {
        try {
            openFromStream(Files.newInputStream(path));
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    private void sync() {
        final ByteBuffer buffer = IntercomHelper.getBuffer();
        nativeGetData(buffer);
        this.duration = Time.us(buffer.asLongBuffer().get(0));
        final IntBuffer intBuffer = buffer.asIntBuffer();
        setData(intBuffer.get(2), intBuffer.get(3));
    }

    /**
     * Gets the total duration of the music.
     *
     * @return the total duration of the music.
     */
    public Time getDuration() {
        return duration;
    }
}
