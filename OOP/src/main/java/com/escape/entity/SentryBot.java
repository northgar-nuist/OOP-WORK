package com.escape.entity;

public class SentryBot extends Enemy {
    private int turnCount;
    private boolean charging;
    private static final int CHARGE_INTERVAL = 2;
    private static final int CHARGED_ATTACK = 90;

    public SentryBot() {
        super("哨兵机器人",
                "一个固定在墙上的重型哨兵炮塔，火力强大但反应较慢。",
                100, 15,
                "\n天花板上的一台哨兵炮塔激活了！红色的瞄准激光扫向你！",
                "哨兵炮塔被摧毁了，金属碎片散落一地。");
        this.turnCount = 0;
        this.charging = false;
    }

    @Override
    public int getAttackPower() {
        return charging ? CHARGED_ATTACK : attackPower;
    }

    public boolean isCharging() { return charging; }

    public void endTurn() {
        turnCount++;
        charging = (turnCount % CHARGE_INTERVAL == 0);
    }

    public String getChargeWarning() {
        return "\n⚠ 哨兵炮塔正在蓄积能量，炮口发出刺眼的白光——下一击会很致命！建议 'defend' 防御。";
    }

    @Override
    public String getAttackMessage() {
        if (charging) {
            return "哨兵炮塔射出高能激光——蓄力重击！！";
        }
        return "哨兵炮塔开火——子弹擦着你的肩膀飞过！";
    }

    @Override
    public String getDeathMessage() {
        if (charging) {
            return "蓄力中的哨兵炮塔射出了致命一击——你倒在了血泊中...";
        }
        return "哨兵炮塔的子弹命中了你——你倒在了血泊中...";
    }
}
