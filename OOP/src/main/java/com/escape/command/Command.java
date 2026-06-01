package com.escape.command;

import com.escape.game.Game;

/** Command pattern: interface for all player commands. */
@FunctionalInterface
public interface Command {
    String execute(Game game);
}
