package com.escape.combat;

import com.escape.entity.Player;
import com.escape.entity.Enemy;

/** Strategy pattern: different combat approaches. */
@FunctionalInterface
public interface CombatStrategy {
    /** Resolve the player's action and return a message describing the result. */
    String execute(Player player, Enemy enemy);
}
