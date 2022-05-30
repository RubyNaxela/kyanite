package com.rubynaxela.kyanite.core;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Pipe for enabling stream from a Java {@link InputStream} into a {@code sf::InputStream}.
 */
@SuppressWarnings("deprecation")
public class SFMLInputStream extends org.jsfml.internal.SFMLInputStream {

    private final InputStream stream;
    private long pos;

    /**
     * Creates an {@code SFMLInputStream} with the specified {@code InputStream} as the source.
     *
     * @param stream the source {@code InputStream}
     */
    public SFMLInputStream(@NotNull InputStream stream) {
        this.stream = stream.markSupported() ? stream : new BufferedInputStream(stream);
        this.stream.mark(Integer.MAX_VALUE);
        pos = 0;
    }

    /**
     * Read data from the stream. After reading, the stream's reading position must be advanced by the amount of bytes read.
     *
     * @param buffer buffer where to copy the read data
     * @param n      desired number of bytes to read
     * @return the number of bytes actually read, or -1 on error
     */
    @Override
    protected long read(ByteBuffer buffer, long n) {
        try {
            byte[] b = new byte[buffer.capacity()];
            long num = stream.read(b);
            if (num == -1) return 0;
            else buffer.put(b);
            pos += num;
            return num;
        } catch (IOException ex) {
            pos = -1;
            return 0;
        }
    }

    /**
     * Change the current reading position.
     *
     * @param position the position to seek to, from the beginning
     * @return the position actually sought to, or -1 on error
     */
    @Override
    protected long seek(long position) {
        try {
            if (position > pos) {
                long skipped = stream.skip(position - pos);
                pos += skipped;
            } else {
                stream.reset();
                pos = 0;
                if (position > 0) pos = stream.skip(position);
            }
            return (pos == position) ? pos : -1;
        } catch (IOException ex) {
            pos = -1;
            return -1;
        }
    }

    /**
     * Get the current reading position in the stream.
     *
     * @return the current position, or -1 on error
     */
    @Override
    protected long tell() {
        return pos;
    }

    /**
     * Return the size of the stream.
     *
     * @return the total number of bytes available in the stream, or -1 on error
     */
    @Override
    protected long getSize() {
        try {
            return stream.available();
        } catch (IOException ignored) {
            return -1;
        }
    }

    /**
     * Called by {@code ~sf::InputStream()}
     */
    @Override
    protected void close() {
        try {
            stream.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Native pointer manager for an {@code SFMLInputStream} bound natively to an {@code sf::InputStream}.
     */
    public static class NativeStreamRef extends NativeRef<SFMLInputStream> {

        @Override
        protected native long nativeInitialize(SFMLInputStream ref);

        @Override
        protected native void nativeRelease(SFMLInputStream ref, long ptr);
    }
}
