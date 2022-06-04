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

import com.rubynaxela.kyanite.window.Window;

import java.util.EventListener;

/**
 * The listener interface for receiving the text event. To add a {@code TextListener},
 * call the {@link Window#addTextListener} with the parameter
 * of an object implementing this interface. When a text character was entered using the
 * keyboard while the window had focus, that object's {@code textEntered} method is invoked.
 *
 * @see TextEvent
 */
public interface TextListener extends EventListener {

    /**
     * Invoked when a text character was entered using the keyboard while the window had focus.
     *
     * @param e the event to be processed
     */
    void textEntered(TextEvent e);
}
