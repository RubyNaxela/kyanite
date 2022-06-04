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

package com.rubynaxela.kyanite.window.event;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Represents text enter events. Objects of this class are created for events of type {@link Event.Type#TEXT_ENTERED}.
 */
public final class TextEvent extends Event {

    private final static Charset utf32 = Charset.forName("UTF-32");

    /**
     * The UTF-32 code of the character that was entered.
     */
    public final int unicode;

    /**
     * The Java representation of the character that was entered.
     */
    public final char character;

    /**
     * Constructs a new text event.
     *
     * @param type    the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param unicode the UTF-32 code of the character that was entered
     */
    public TextEvent(int type, int unicode) {
        super(type);

        this.unicode = unicode;

        final ByteBuffer unicodeBuffer = ByteBuffer.allocate(4);
        unicodeBuffer.putInt(unicode);
        unicodeBuffer.flip();

        final CharBuffer chars = utf32.decode(unicodeBuffer);
        character = chars.get();
    }
}
