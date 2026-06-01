package com.escape.command;

import com.escape.game.Game;

public class HelpCommand implements Command {

    @Override
    public String execute(Game game) {
        return """
                --- 可用命令 ---
                方向键: n(北) s(南) e(东) w(西) — 移动到相邻房间
                go <方向>          — 同上
                look                — 查看当前房间
                take <物品名>       — 拾取物品
                inventory / i       — 查看背包
                use <物品名>        — 使用物品
                use <答案> on puzzle — 尝试解答谜题
                fight / attack      — 攻击敌人
                defend              — 防御姿态（减少伤害）
                hint                — 获取提示（共3次）
                help                — 显示此帮助
                quit                — 退出游戏
                """;
    }
}
