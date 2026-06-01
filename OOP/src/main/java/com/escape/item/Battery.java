package com.escape.item;

public class Battery extends Item {
    private final int oxygenBoost;

    public Battery(int oxygenBoost) {
        super("电池", "一块备用电池，可以为维生系统补充氧气。");
        this.oxygenBoost = oxygenBoost;
    }

    public int getOxygenBoost() { return oxygenBoost; }

    @Override
    public String useEffect() {
        return "你给维生系统更换了电池，氧气供应恢复了" + oxygenBoost + "个单位。";
    }
}
