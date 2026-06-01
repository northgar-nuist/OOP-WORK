package com.escape.entity;

import com.escape.game.GameConfig;
import com.escape.item.Item;
import com.escape.world.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private Room currentRoom;
    private int health;
    private int oxygen;
    private int hintsRemaining;
    private final List<Item> inventory;
    private Item weapon;

    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.health = GameConfig.MAX_HEALTH;
        this.oxygen = GameConfig.MAX_OXYGEN;
        this.hintsRemaining = 3;
        this.inventory = new ArrayList<>();
        this.weapon = null;
    }

    public Room getCurrentRoom() { return currentRoom; }

    public void setCurrentRoom(Room room) { this.currentRoom = room; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, Math.min(GameConfig.MAX_HEALTH, health)); }
    public void takeDamage(int damage) { this.health = Math.max(0, this.health - damage); }
    public void heal(int amount) { this.health = Math.min(GameConfig.MAX_HEALTH, this.health + amount); }

    public int getOxygen() { return oxygen; }
    public void setOxygen(int oxygen) { this.oxygen = Math.max(0, oxygen); }
    public void addOxygen(int amount) { this.oxygen += amount; }
    public void tickOxygen() { this.oxygen = Math.max(0, this.oxygen - 1); }

    public List<Item> getInventory() { return Collections.unmodifiableList(inventory); }

    public void addItem(Item item) { inventory.add(item); }

    public Item removeItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.remove(item);
                return item;
            }
        }
        return null;
    }

    public boolean hasItem(String itemName) {
        return inventory.stream().anyMatch(i -> i.getName().equalsIgnoreCase(itemName));
    }

    public Item getWeapon() { return weapon; }

    public void equipWeapon(Item weapon) {
        if (this.weapon != null) {
            inventory.add(this.weapon);
        }
        inventory.remove(weapon);
        this.weapon = weapon;
    }

    public int getAttackPower() {
        int base = GameConfig.DEFAULT_ATTACK;
        if (weapon instanceof com.escape.item.Weapon w) {
            base += w.getDamageBonus();
        }
        return base;
    }

    public boolean isAlive() { return health > 0; }

    public int getHintsRemaining() { return hintsRemaining; }
    public boolean useHint() {
        if (hintsRemaining > 0) { hintsRemaining--; return true; }
        return false;
    }
}
