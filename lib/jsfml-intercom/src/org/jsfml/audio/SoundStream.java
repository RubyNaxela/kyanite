/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.audio;

import com.rubynaxela.kyanite.audio.SoundSource;
import com.rubynaxela.kyanite.audio.SoundStream.Chunk;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.core.Intercom;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.audio.SoundStream} and SFML's {@code sf::SoundStream}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class SoundStream extends SoundSource {

    protected SoundStream() {
    }

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    public native void play();

    public native void pause();

    public native void stop();

    public abstract int getChannelCount();

    public abstract int getSampleRate();

    protected native void nativeSetPlayingOffset(long offset);

    public abstract Time getPlayingOffset();

    public abstract void setPlayingOffset(Time offset);

    protected native void nativeSetLoop(boolean loop);

    public abstract boolean isLoop();

    public abstract void setLoop(boolean loop);

    @Override
    protected native int nativeGetStatus();

    @Override
    public Status getStatus() {
        return super.getStatus();
    }

    protected abstract void setData(int channelCount, int sampleRate);

    protected native void nativeInitialize(int channelCount, int sampleRate);

    protected abstract void initialize(int channelCount, int sampleRate);

    @Intercom
    @SuppressWarnings("unused")
    protected abstract Buffer onGetDataInternal();

    protected abstract Chunk onGetData();

    @Intercom
    @SuppressWarnings("unused")
    protected abstract void onSeekInternal(long time);

    protected abstract void onSeek(Time time);
}
