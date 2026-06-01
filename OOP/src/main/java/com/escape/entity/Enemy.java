package com.escape.entity;

public abstract class Enemy {
    protected final String name;
    protected final String description;
    protected int health;
    protected final int attackPower;
    protected final String encounterMessage;
    protected final String defeatMessage;

    protected Enemy(String name, String description, int health, int attackPower,
                    String encounterMessage, String defeatMessage) {
        this.name = name;
        this.description = description;
        this.health = health;
        this.attackPower = attackPower;
        this.encounterMessage = encounterMessage;
        this.defeatMessage = defeatMessage;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHealth() { return health; }
    public int getAttackPower() { return attackPower; }
    public boolean isAlive() { return health > 0; }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public String getEncounterMessage() { return encounterMessage; }
    public String getDefeatMessage() { return defeatMessage; }

    public boolean isCharging() { return false; }
    public String getChargeWarning() { return null; }
    public void endTurn() { /* no-op by default */ }

    public abstract String getAttackMessage();
    public abstract String getDeathMessage();
}
