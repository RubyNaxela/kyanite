package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.core.NativeRef;
import com.rubynaxela.kyanite.core.SFMLErrorCapture;
import com.rubynaxela.kyanite.core.StreamUtil;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

/**
 * Holds a character font for use in text displays.
 */
@SuppressWarnings("deprecation")
public class Font extends org.jsfml.graphics.Font {

    private final Map<Integer, SizeInfo> sizeInfos = new TreeMap<>();

    /**
     * Memory reference and heap pointer that keeps alive the data input stream for freetype.
     */
    private final NativeRef<byte[]> memoryRef = new NativeRef<>() {
        @Override
        protected long nativeInitialize(byte[] ref) {
            return nativeLoadFromMemory(ref);
        }

        @Override
        protected void nativeRelease(byte[] ref, long ptr) {
            nativeReleaseMemory(ref, ptr);
        }
    };

    /**
     * Constructs a new font.
     */
    public Font() {
    }

    /**
     * Fully loads all available bytes from an {@link java.io.InputStream} and attempts to load the texture from it.
     *
     * @param in the input stream to read from
     * @throws IOException in case an I/O error occurs
     */
    public void loadFromStream(@NotNull InputStream in) throws IOException {
        SFMLErrorCapture.start();
        memoryRef.initialize(StreamUtil.readStream(in));
        final String msg = SFMLErrorCapture.finish();
        if (memoryRef.isNull()) throw new IOException(msg);
    }

    /**
     * Attempts to load the texture from a file.
     *
     * @param path the path to the file to load the texture from
     * @throws IOException in case an I/O error occurs
     */
    public void loadFromFile(@NotNull Path path) throws IOException {
        SFMLErrorCapture.start();
        memoryRef.initialize(StreamUtil.readFile(path));
        final String msg = SFMLErrorCapture.finish();
        if (memoryRef.isNull()) throw new IOException(msg);
    }

    private SizeInfo getSizeInfo(int characterSize) {
        SizeInfo info = sizeInfos.get(characterSize);
        if (info == null) {
            final int lineSpacing = nativeGetLineSpacing(characterSize);
            final long p = nativeGetTexture(characterSize);
            final ConstTexture tex = (p != 0) ? new Texture(p) : null;
            info = new SizeInfo(characterSize, lineSpacing, tex);
            sizeInfos.put(characterSize, info);
        }
        return info;
    }

    @Override
    public Glyph getGlyph(int unicode, int characterSize, boolean bold) {
        final SizeInfo info = getSizeInfo(characterSize);
        final Map<Integer, Glyph> glyphMap = bold ? info.boldGlyphs : info.glyphs;
        Glyph glyph = glyphMap.get(unicode);
        if (glyph == null) {
            final IntBuffer buf = IntercomHelper.getBuffer().asIntBuffer();
            nativeGetGlyph(unicode, characterSize, bold, buf);
            glyph = new Glyph(buf.get(0),
                              new IntRect(buf.get(1), buf.get(2), buf.get(3), buf.get(4)),
                              new IntRect(buf.get(5), buf.get(6), buf.get(7), buf.get(8)));
            glyphMap.put(unicode, glyph);
        }
        return glyph;
    }

    @Override
    public int getKerning(int first, int second, int characterSize) {
        final SizeInfo info = getSizeInfo(characterSize);
        final long x = ((long) first << 32) | (long) second;
        Integer kerning = info.kerning.get(x);
        if (kerning == null) {
            kerning = nativeGetKerning(first, second, characterSize);
            info.kerning.put(x, kerning);
        }
        return kerning;
    }

    @Override
    public int getLineSpacing(int characterSize) {
        return getSizeInfo(characterSize).lineSpacing;
    }

    @Override
    public ConstTexture getTexture(int characterSize) {
        return getSizeInfo(characterSize).texture;
    }

    private static class SizeInfo {

        final int characterSize, lineSpacing;
        final ConstTexture texture;
        final Map<Long, Integer> kerning = new TreeMap<>();
        final Map<Integer, Glyph> glyphs = new TreeMap<>(), boldGlyphs = new TreeMap<>();

        SizeInfo(int characterSize, int lineSpacing, ConstTexture texture) {
            this.characterSize = characterSize;
            this.lineSpacing = lineSpacing;
            this.texture = texture;
        }
    }
}
