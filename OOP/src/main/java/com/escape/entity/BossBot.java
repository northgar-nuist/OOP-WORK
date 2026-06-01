package com.escape.entity;

public class BossBot extends Enemy {

    public BossBot() {
        super("安保主管机器人",
                "一台体型巨大的人形机甲，红色独眼闪烁着冷酷的光芒。它是空间站的安保主管。",
                100, 20,
                "\n一个低沉冰冷的机械音响起：\"未授权人员。终止程序启动。\"\n安保主管机器人从黑暗中走了出来，每一步都让地板震颤！",
                "安保主管机器人的独眼熄灭了，庞大的身躯轰然倒地。");
    }

    @Override
    public String getAttackMessage() {
        return "安保主管机器人的机械臂重重砸下！";
    }

    @Override
    public String getDeathMessage() {
        return "安保主管机器人的机械臂将你击倒在地——";
    }
}
