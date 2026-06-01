package com.escape.world;

import com.escape.item.Item;
import com.escape.entity.Enemy;

import java.util.*;

public abstract class Room {
    protected final String name;
    protected final String description;
    protected final Map<String, Room> exits;
    protected final List<Item> items;
    protected Enemy enemy;

    protected Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new LinkedHashMap<>();
        this.items = new ArrayList<>();
        this.enemy = null;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public void addExit(String direction, Room room) {
        exits.put(direction, room);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public boolean hasExit(String direction) {
        return exits.containsKey(direction);
    }

    public String getExitsDescription() {
        if (exits.isEmpty()) return "没有明显的出口。";
        StringBuilder sb = new StringBuilder("出口: ");
        for (String dir : exits.keySet()) {
            Room target = exits.get(dir);
            sb.append(String.format("[%s] %s  ", dir, target.getName()));
        }
        return sb.toString().trim();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public List<Item> getItems() { return Collections.unmodifiableList(items); }

    public String getItemsDescription() {
        if (items.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append("[").append(item.getName()).append("] ");
        }
        return sb.toString().trim();
    }

    public boolean hasEnemy() { return enemy != null && enemy.isAlive(); }

    public Enemy getEnemy() { return enemy; }

    public void setEnemy(Enemy enemy) { this.enemy = enemy; }

    /** Whether the player can pass through this room in a given direction. */
    public boolean canPass(String direction) {
        return hasExit(direction);
    }

    /** Called when the player enters this room. Return extra message or null. */
    public String onEnter() { return null; }
}
