package com.escape.world;

import com.escape.entity.Enemy;

public class BattleRoom extends Room {
    private boolean cleared;
    private final String lockedDirection;

    public BattleRoom(String name, String description, Enemy enemy, String lockedDirection) {
        super(name, description);
        this.enemy = enemy;
        this.cleared = false;
        this.lockedDirection = lockedDirection;
    }

    @Override
    public boolean canPass(String direction) {
        // Only block the forward exit — player can retreat
        if (!cleared && enemy != null && enemy.isAlive()
                && direction.equalsIgnoreCase(lockedDirection)) {
            return false;
        }
        return hasExit(direction);
    }

    @Override
    public String onEnter() {
        if (!cleared && enemy != null && enemy.isAlive()) {
            cleared = false;
            return enemy.getEncounterMessage();
        }
        return null;
    }

    public void markCleared() { this.cleared = true; }
    public boolean isCleared() { return cleared; }
}
