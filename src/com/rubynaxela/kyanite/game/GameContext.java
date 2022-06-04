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

package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.window.VideoMode;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Provides a set of references to the basic objects used by the game. The instance of this class can be accessed via the
 * {@link GameContext#getInstance} method. Only one {@code GameContext} instance can exist in a single game instance.
 */
public final class GameContext {

    static GameContext instance;
    private final Game gameInstance;
    private final AssetsBundle assetsBundle;
    private final AudioHandler audioHandler;
    private final Clock clock;
    private final Map<String, Object> resources;
    private Window window;

    GameContext(@NotNull Game gameInstance) {
        this.gameInstance = gameInstance;
        assetsBundle = new AssetsBundle();
        audioHandler = new AudioHandler(this);
        clock = new Clock(false);
        resources = new HashMap<>();
    }

    /**
     * @return reference to the instance of the game context
     */
    public static GameContext getInstance() {
        return instance;
    }

    /**
     * Sets the window size and title. This has to be called before the window is used.
     * If this method has been called previously, window initializaion is skipped.
     *
     * @param size  the window size
     * @param title the window title
     * @return the reference to the window
     */
    @Contract("_, _ -> new")
    public Window setupWindow(@NotNull Vector2i size, @NotNull String title) {
        return (window == null) ? window = new Window(new VideoMode(size.x, size.y), title, audioHandler) : window;
    }

    /**
     * Sets the window size and title. This has to be called before the window is used.
     * If this method has been called previously, window initializaion is skipped.
     *
     * @param width  the window width
     * @param height the window height
     * @param title  the window title
     * @return the reference to the window
     */
    @Contract("_, _, _ -> new")
    public Window setupWindow(int width, int height, @NotNull String title) {
        return setupWindow(Vec2.i(width, height), title);
    }

    /**
     * Sets the window size. This has to be called before the window is used.
     * If this method has been called previously, window initializaion is skipped.
     *
     * @param size the window size
     * @return the reference to the window
     */
    @Contract("_ -> new")
    public Window setupWindow(@NotNull Vector2i size) {
        return setupWindow(size, "");
    }

    /**
     * Sets the window size. This has to be called before the window is used.
     * If this method has been called previously, window initializaion is skipped.
     *
     * @param width  the window width
     * @param height the window height
     * @return the reference to the window
     */
    @Contract("_, _ -> new")
    public Window setupWindow(int width, int height) {
        return setupWindow(Vec2.i(width, height), "");
    }

    /**
     * @return whether the window has been already initialized
     */
    public boolean isWindowInitialized() {
        return window != null;
    }

    /**
     * @return reference to the game window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * @return reference to the game assets bundle
     */
    @NotNull
    public AssetsBundle getAssetsBundle() {
        return assetsBundle;
    }

    /**
     * @return reference to the game audio handler
     */
    @NotNull
    public AudioHandler getAudioHandler() {
        return audioHandler;
    }

    /**
     * @return reference to the game clock
     */
    @NotNull
    public Clock getClock() {
        return clock;
    }

    /**
     * Stops all sounds, restarts the game clock and re-creates the game window. Does
     * not stop any actions that are not handled by the game loop, e.g. running threads.
     */
    public void restartGame() {
        final Window oldWindow = window;
        final Vector2i oldWindowSize = oldWindow.getSize();
        window = new Window(new VideoMode(oldWindowSize.x, oldWindowSize.y), oldWindow.getTitle(), audioHandler);
        window.setPosition(oldWindow.getPosition());
        oldWindow.close();
        audioHandler.stopAllSounds();
        gameInstance.init();
        clock.restart();
        window.startLoop();
    }

    /**
     * Stores a resource in the game context storage which can be later retrieved using the specified key. The resource
     * can be any object that will be used in multiple places throughout the game, for instance, game progress data.
     *
     * @param key   the resource key
     * @param value the resource to be stored
     */
    public void putResource(@NotNull String key, @Nullable Object value) {
        resources.put(key, value);
    }

    /**
     * Returns the resource associated with the specified key.
     *
     * @param key a resource key
     * @param <T> the type of the resource
     * @return the resource associated with the specified key
     * @throws ClassCastException     if the generic type doesn't match the type of the resource
     * @throws NoSuchElementException if the specified key has no associated resource
     */
    @SuppressWarnings("unchecked")
    public <T> T getResource(@NotNull String key) {
        if (resources.containsKey(key)) return (T) resources.get(key);
        else throw new NoSuchElementException("Resource of ID " + key + " either does not exist or " +
                                              "was attempted to be used before being registered");
    }

    /**
     * Gets the class of the game instance (which has been passed to the {@link Game#run} method).
     *
     * @return the main game class
     */
    public Class<? extends Game> getGameInstanceClass() {
        return gameInstance.getClass();
    }
}
