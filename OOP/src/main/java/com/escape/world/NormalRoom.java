package com.escape.world;

public class NormalRoom extends Room {
    private final String lockedDirection;
    private final String requiredItem;

    public NormalRoom(String name, String description) {
        super(name, description);
        this.lockedDirection = null;
        this.requiredItem = null;
    }

    public NormalRoom(String name, String description, String lockedDirection, String requiredItem) {
        super(name, description);
        this.lockedDirection = lockedDirection;
        this.requiredItem = requiredItem;
    }

    @Override
    public boolean canPass(String direction) {
        if (direction.equalsIgnoreCase(lockedDirection)) {
            for (var item : items) {
                if (item.getName().equalsIgnoreCase(requiredItem)) {
                    return false; // key is in the room, not yet taken
                }
            }
            // If key is not in the room AND direction is locked, player must carry it
            return true; // actual check is done in GoCommand
        }
        return hasExit(direction);
    }

    public String getLockedDirection() { return lockedDirection; }
    public String getRequiredItem() { return requiredItem; }
}
