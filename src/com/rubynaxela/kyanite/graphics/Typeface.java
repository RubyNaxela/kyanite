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

package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.core.NativeRef;
import com.rubynaxela.kyanite.core.SFMLErrorCapture;
import com.rubynaxela.kyanite.core.StreamUtil;
import com.rubynaxela.kyanite.game.assets.Asset;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Holds a character typeface for use in text displays.
 */
@SuppressWarnings("deprecation")
public class Typeface extends org.jsfml.graphics.Font implements ConstTypeface, Asset {

    /**
     * JetBrains Mono, a built-in typeface.
     */
    public static final Typeface JETBRAINS_MONO = new Typeface(
            Objects.requireNonNull(Typeface.class.getResourceAsStream("/res/jetbrains_mono.ttf")));

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
     * Creates a new font from the source specified by the path.
     *
     * @param path path to the font file
     * @throws IOException in case an I/O error occurs
     */
    public Typeface(@NotNull Path path) {
        loadFromFile(path);
    }

    /**
     * Creates a new font from the source specified by the path.
     *
     * @param path path to the font file
     * @throws IOException in case an I/O error occurs
     */
    public Typeface(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new font from the source font file.
     *
     * @param file the font file
     * @throws IOException in case an I/O error occurs
     */
    public Typeface(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new font from the input stream.
     *
     * @param stream the font data input stream
     * @throws IOException in case an I/O error occurs
     */
    public Typeface(@NotNull InputStream stream) {
        loadFromStream(stream);
    }

    /**
     * Fully loads all available bytes from an {@link java.io.InputStream} and attempts to load the texture from it.
     *
     * @param in the input stream to read from
     * @throws IOException in case an I/O error occurs
     */
    private void loadFromStream(@NotNull InputStream in) throws IOException {
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
    private void loadFromFile(@NotNull Path path) throws IOException {
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

    /**
     * Gets a glyph information structure from the font.
     *
     * @param unicode       the unicode (UTF-32) of the character to retrieve the glyph for
     * @param characterSize the character size in question
     * @param bold          {@code true} if the bold glyph version should be returned, {@code false} for the regular version
     * @return the {@link Glyph} representing the given unicode character
     */
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

    /**
     * Gets the kerning offset between two glyphs.
     *
     * @param first         the unicode (UTF-32) of the first character
     * @param second        the unicode (UTF-32) of the second character
     * @param characterSize the character size in question
     * @return the kerning offset between the two glyphs
     */
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

    /**
     * Gets the line spacing of the font.
     *
     * @param characterSize the character size in question
     * @return the line spacing of the font
     */
    @Override
    public int getLineSpacing(int characterSize) {
        return getSizeInfo(characterSize).lineSpacing;
    }

    /**
     * Retrieves the texture containing the font's glyphs. The texture returned is immutable.
     *
     * @param characterSize the character size in question
     * @return the texture containing the font's glyphs of the character given size
     */
    @Override
    public ConstTexture getTexture(int characterSize) {
        return getSizeInfo(characterSize).texture;
    }

    private static class SizeInfo {

        final int characterSize, lineSpacing;
        final ConstTexture texture;
        final Map<Long, Integer> kerning = new TreeMap<>();
        final Map<Integer, Glyph> glyphs = new TreeMap<>(), boldGlyphs = new TreeMap<>();

        SizeInfo(int characterSize, int lineSpacing, @Nullable ConstTexture texture) {
            this.characterSize = characterSize;
            this.lineSpacing = lineSpacing;
            this.texture = texture;
        }
    }
}
