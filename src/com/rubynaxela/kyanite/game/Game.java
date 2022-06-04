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

import com.rubynaxela.kyanite.core.KyaniteException;
import com.rubynaxela.kyanite.system.SystemUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

/**
 * The game root class. Provides a {@link GameContext} object, which
 * provides a set of references to the basic objects used by the game.
 */
public abstract class Game {

    private final GameContext context = GameContext.instance = new GameContext(this);

    /**
     * Starts the game by executing the {@code init()} method and then starting the clock and the window loop.
     *
     * @param gameClass the main game class
     * @param args      the {@code args} argument from the application {@code main()} method
     */
    public static void run(@NotNull Class<? extends Game> gameClass, @NotNull String[] args) {
        SystemUtils.restartIfNecessary(args);
        try {
            final Game game = gameClass.getConstructor().newInstance();
            game.preInit();
            if (game.context.getWindow() != null)
                throw new IllegalStateException("A game window should not be created by the preInit() method. " +
                                                "Please run getContext().setupWindow in the init() method");
            game.init();
            if (game.context.getWindow() == null)
                throw new NullPointerException("A game window has not been created. Please run " +
                                               "getContext().setupWindow in the init() method of the game class");
            game.context.getClock().tryStart();
            game.context.getWindow().startLoop();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new KyaniteException("The " + gameClass.getName() + " must have a default constructor");
        }
    }

    /**
     * This method is called only when the game is launched. This is the proper place to register assets and execute
     * any other actions that do not need to be executed when the {@link GameContext#restartGame} method is invoked.
     */
    protected abstract void preInit();

    /**
     * This method is called when the game is launched and after every {@link GameContext#restartGame}
     * method call. This is  the proper place to put actions that set the initial state of the game,
     * for instance, creating a scene/HUD and assigning it to the window or setting up global objects.
     */
    protected abstract void init();

    /**
     * @return the reference to the game context
     */
    public GameContext getContext() {
        return context;
    }
}
