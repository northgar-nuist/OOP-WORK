package com.escape.command;

import com.escape.game.Game;
import com.escape.world.Room;
import com.escape.world.PuzzleRoom;
import com.escape.world.BattleRoom;

public class LookCommand implements Command {

    @Override
    public String execute(Game game) {
        Room current = game.getCurrentRoom();
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- ").append(current.getName()).append(" ---\n");
        sb.append(current.getDescription()).append("\n");
        sb.append(current.getExitsDescription());

        if (!current.getItems().isEmpty()) {
            sb.append("\n这里可以看到: ").append(current.getItemsDescription());
        }

        if (current instanceof BattleRoom br && br.hasEnemy()) {
            sb.append("\n\n⚠ 有敌人在此：").append(br.getEnemy().getName())
              .append("（生命值 ").append(br.getEnemy().getHealth()).append("）");
        }

        if (current instanceof PuzzleRoom pr && !pr.isSolved()) {
            sb.append("\n\n").append(pr.getPuzzleHint());
        }

        sb.append("\n氧气剩余: ").append(game.getPlayer().getOxygen())
          .append(" | 生命值: ").append(game.getPlayer().getHealth());

        return sb.toString();
    }
}
