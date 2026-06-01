package com.escape.util;

/** Observer pattern: notified when significant game events occur. */
public interface GameObserver {
    void onRoomEntered(String roomName);
    void onItemTaken(String itemName);
    void onEnemyDefeated(String enemyName);
    void onGameOver(String reason);
}
