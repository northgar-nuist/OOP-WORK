package com.escape.command;

import com.escape.game.Game;
import com.escape.world.*;

public class HintCommand implements Command {

    @Override
    public String execute(Game game) {
        var player = game.getPlayer();

        if (!player.useHint()) {
            return "你的直觉已经用尽了——没有更多提示了。靠自己走下去吧。";
        }

        Room current = game.getCurrentRoom();
        String roomName = current.getName();
        int remaining = player.getHintsRemaining();

        String hint = switch (roomName) {
            case "船员舱" -> "你刚从昏迷中醒来，看看房间里有什么能带走的。";
            case "走廊A" -> {
                if (!player.hasItem("门禁卡") || !player.hasItem("扳手"))
                    yield "先往北去储藏室找找物资，别急着往东——那边有东西在等着你。";
                if (player.hasItem("门禁卡") && player.hasItem("扳手")
                        && player.getWeapon() == null)
                    yield "你的背包里有武器，用 'use 扳手' 装备它。";
                yield "准备好了就往东走，面对哨兵机器人时可以用 'fight' 攻击、'defend' 防御。";
            }
            case "储藏室" -> {
                if (!player.hasItem("扳手"))
                    yield "工具箱旁边有一把扳手，捡起来装备上。";
                if (!player.hasItem("门禁卡"))
                    yield "货架上有张门禁卡，别漏了。";
                yield "北面是医疗室，去那里补充物资。";
            }
            case "医疗室" -> {
                if (!player.hasItem("医疗包"))
                    yield "检查台下面的抽屉里有个医疗包，别忘了拿。";
                yield "搜刮完了就往南回去，准备好战斗。";
            }
            case "安保室" -> {
                if (current instanceof BattleRoom br && br.hasEnemy())
                    yield "哨兵机器人火力很强。如果你受伤了可以用 'use 医疗包' 治疗。打不过可以往西撤退。";
                yield "敌人已清除，继续往东走。";
            }
            case "机舱" -> {
                if (current instanceof PuzzleRoom pr && !pr.isSolved())
                    yield "便签上的那句话有点耳熟... 好像出自一部经典科幻电影，电影名就是一本'指南'。";
                yield "电路已经修好，往东走吧。";
            }
            case "走廊B" -> {
                if (!player.hasItem("门禁卡"))
                    yield "你需要一张门禁卡。应该在之前的某个房间里。";
                yield "刷卡往东就能到逃生舱了！";
            }
            default -> "仔细看看四周，搜索你所在的房间，收集有用的物品。";
        };

        return "💡 提示 (" + remaining + "/3 剩余):\n" + hint;
    }
}
