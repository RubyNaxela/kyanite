package com.rubynaxela.kyanite.game;

/**
 * Provides easier access to the global game context instance
 */
public interface Context {

    /**
     * @return the global game context instance
     */
    default GameContext getContext() {
        return GameContext.getInstance();
    }
}
