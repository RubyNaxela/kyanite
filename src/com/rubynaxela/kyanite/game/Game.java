package com.rubynaxela.kyanite.game;

/**
 * The game root class. Provides a {@link GameContext} object, which
 * provides a set of references to the basic objects used by the game.
 */
public abstract class Game {

    private final GameContext context = GameContext.instance = new GameContext(this);

    /**
     * Starts the game clock and the window loop.
     */
    public final void start() {
        init();
        context.getClock().tryStart();
        context.getWindow().startLoop();
    }

    /**
     * This method is called when the game is started or restarted. This is a proper place to put actions that
     * set the initial state of the game. However, global actions, like asset registration, should be executed
     * inside the game's constructor. Note that if this method uses the game window, it should not rely on
     * the value returned by {@code GameContext.setupWindow()}. Instead, the window should be retrieved using
     * the {@code getContext().getWindow()} method because after a restart, the game window is a new object.
     */
    protected void init() {
    }

    /**
     * @return the reference to the game context
     */
    public GameContext getContext() {
        return context;
    }
}
