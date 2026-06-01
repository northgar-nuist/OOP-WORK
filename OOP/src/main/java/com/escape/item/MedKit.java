package com.escape.item;

public class MedKit extends Item {
    private final int healAmount;

    public MedKit(int healAmount) {
        super("医疗包", "一个标准急救包，可以治疗伤势。");
        this.healAmount = healAmount;
    }

    public int getHealAmount() { return healAmount; }

    @Override
    public String useEffect() {
        return "你使用医疗包处理了伤口，恢复了" + healAmount + "点生命值。";
    }
}
