package com.escape.command;

import com.escape.game.Game;
import com.escape.game.GameState;
import com.escape.combat.CombatSystem;
import com.escape.entity.Player;
import com.escape.entity.Enemy;
import com.escape.world.Room;
import com.escape.world.BattleRoom;

public class FightCommand implements Command {
    private final String action; // "attack" or "defend"

    public FightCommand(String action) {
        this.action = action != null ? action : "attack";
    }

    @Override
    public String execute(Game game) {
        Room current = game.getCurrentRoom();
        Player player = game.getPlayer();

        if (!(current instanceof BattleRoom br)) {
            return "这里没有敌人可战斗。";
        }

        Enemy enemy = br.getEnemy();
        if (enemy == null || !enemy.isAlive()) {
            return "这里的敌人已经被击败了。";
        }

        StringBuilder result = new StringBuilder();
        boolean defending = "defend".equals(action);

        // Advance turn counter (may set charging state)
        enemy.endTurn();

        // Show warning if enemy just entered charging state
        if (enemy.isCharging()) {
            String warning = enemy.getChargeWarning();
            if (warning != null) {
                result.append(warning).append("\n");
            }
        }

        // Player action
        if (defending) {
            result.append(CombatSystem.resolveDefend(player, enemy));
        } else {
            result.append(CombatSystem.resolvePlayerAttack(player, enemy));
        }

        // Enemy attacks (if still alive)
        if (enemy.isAlive()) {
            result.append("\n");
            if (defending) {
                result.append(CombatSystem.resolveEnemyAttackOnDefending(player, enemy));
            } else {
                result.append(CombatSystem.resolveEnemyAttack(player, enemy));
            }
        }

        // Check player death
        if (!player.isAlive()) {
            game.setState(GameState.LOST_HEALTH);
            result.append("\n\n").append(enemy.getDeathMessage());
            result.append("\n【游戏结束 — 你被击败了】");
            return result.toString();
        }

        // Check enemy death
        if (!enemy.isAlive()) {
            result.append("\n\n").append(enemy.getDefeatMessage());
            br.markCleared();
            result.append("\n你可以离开这个房间了。");
        }

        return result.toString();
    }
}
