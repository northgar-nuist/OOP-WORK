package com.escape.entity;

public class PatrolBot extends Enemy {

    public PatrolBot() {
        super("巡逻机器人",
                "一个圆盘状的巡逻机器人，装备有电击枪。",
                40, 8,
                "\n警告！一个巡逻机器人检测到了你的存在。它发出刺耳的警报声，向你冲了过来！",
                "巡逻机器人冒着电火花倒在地上，彻底报废了。");
    }

    @Override
    public String getAttackMessage() {
        return "巡逻机器人射出电弧——你被电击了！";
    }

    @Override
    public String getDeathMessage() {
        return "巡逻机器人的电击枪击穿了你的防护服——";
    }
}
