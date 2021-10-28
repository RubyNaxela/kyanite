package com.rubynaxela.kyanite.game;

public abstract class Game {

    private final GameContext context = GameContext.getInstance();

    /**
     * Starts the game clock and the window loop
     */
    public final void start() {
        context.getClock().tryStart();
        context.getWindow().startLoop();
    }

    /**
     * @return the reference to the game context
     */
    public GameContext getContext() {
        return context;
    }
}
