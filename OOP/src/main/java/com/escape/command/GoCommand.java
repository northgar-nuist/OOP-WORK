package com.escape.command;

import com.escape.game.Game;
import com.escape.game.GameState;
import com.escape.world.Room;
import com.escape.world.BattleRoom;
import com.escape.world.ExitRoom;
import com.escape.world.NormalRoom;
import com.escape.item.KeyCard;

public class GoCommand implements Command {
    private final String direction;

    public GoCommand(String direction) {
        this.direction = direction;
    }

    @Override
    public String execute(Game game) {
        Room current = game.getCurrentRoom();

        if (!current.hasExit(direction)) {
            return "那边没有路。可用方向：" + current.getExitsDescription();
        }

        if (!current.canPass(direction)) {
            if (current instanceof BattleRoom br && !br.isCleared()) {
                return "机器人挡在了这个方向！先击败它才能前进（你可以原路撤退）。";
            }
            // Check for locked door
            if (current instanceof NormalRoom nr
                    && direction.equalsIgnoreCase(nr.getLockedDirection())) {
                return "门禁系统锁住了。你需要" + nr.getRequiredItem() + "才能通过。";
            }
            return "此路不通！你需要先解决当前房间的问题。";
        }

        // Check if destination is locked (keycard check)
        Room next = current.getExit(direction);
        if (next instanceof NormalRoom nr
                && nr.getLockedDirection() != null) {
            // The destination is a locked room — check if player has the key
            String required = nr.getRequiredItem();
            if (required != null && !game.getPlayer().hasItem(required)) {
                return "门禁系统发出警告：需要" + required + "才能进入" + nr.getName() + "。";
            }
        }

        game.getPlayer().setCurrentRoom(next);
        StringBuilder result = new StringBuilder();
        result.append("你向").append(direction).append("走去——进入了").append(next.getName()).append("。");

        // On-enter trigger
        String enterMsg = next.onEnter();
        if (enterMsg != null) {
            result.append(enterMsg);
        }

        // Check for ExitRoom win
        if (next instanceof ExitRoom) {
            game.setState(GameState.WON);
            result.append("\n\n你按下了发射按钮。逃生舱从空间站弹射而出——");
            result.append("\n透过舷窗回望，深空号在身后逐渐缩小，爆炸的火光无声地绽放在真空中。");
            result.append("\n你靠在座椅上长长地吐了一口气。呼吸化作白雾，在舱内缓缓散去。");
            result.append("\n回家了。");
            result.append("\n\n===========================================");
            result.append("\n  ★ 恭喜你成功逃离了深空号空间站！★");
            result.append("\n===========================================");
            return result.toString();
        }

        // Show room info
        result.append("\n");
        result.append("\n--- ").append(next.getName()).append(" ---");
        result.append("\n").append(next.getDescription());
        result.append("\n").append(next.getExitsDescription());
        if (!next.getItems().isEmpty()) {
            result.append("\n这里可以看到: ").append(next.getItemsDescription());
        }
        result.append("\n氧气剩余: ").append(game.getPlayer().getOxygen())
              .append(" | 生命值: ").append(game.getPlayer().getHealth());

        return result.toString();
    }
}
