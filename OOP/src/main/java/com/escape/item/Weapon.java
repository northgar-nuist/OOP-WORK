package com.escape.item;

public class Weapon extends Item {
    private final int damageBonus;

    public Weapon(String name, int damageBonus) {
        super(name, "一把武器，攻击力+" + damageBonus + "。");
        this.damageBonus = damageBonus;
    }

    public int getDamageBonus() { return damageBonus; }

    @Override
    public String useEffect() {
        return "你装备了" + name + "，攻击力提升了！";
    }
}
