package com.escape.combat;

import com.escape.entity.Player;
import com.escape.entity.Enemy;

/** Resolves combat interactions between player and enemies. */
public final class CombatSystem {
    private static final CombatStrategy attackStrategy = new NormalAttack();
    private static final CombatStrategy defendStrategy = new DefensiveStance();

    private CombatSystem() {}

    public static String resolvePlayerAttack(Player player, Enemy enemy) {
        return attackStrategy.execute(player, enemy);
    }

    public static String resolveDefend(Player player, Enemy enemy) {
        return defendStrategy.execute(player, enemy);
    }

    public static String resolveEnemyAttack(Player player, Enemy enemy) {
        // If player chose defend last round, halve damage
        int damage = enemy.getAttackPower() + (int) (Math.random() * 6) - 3;
        damage = Math.max(1, damage);

        // Check if the last player action was defend (tracked via a simple flag approach)
        // For simplicity, we manage this in FightCommand context
        player.takeDamage(damage);

        StringBuilder sb = new StringBuilder();
        sb.append(enemy.getAttackMessage());
        sb.append(" 受到了 ").append(damage).append(" 点伤害。");
        if (player.isAlive()) {
            sb.append(" 剩余生命值：").append(player.getHealth()).append("。");
        }
        return sb.toString();
    }

    /** Resolve enemy attack on a defending player (half damage). */
    public static String resolveEnemyAttackOnDefending(Player player, Enemy enemy) {
        int damage = enemy.getAttackPower() + (int) (Math.random() * 6) - 3;
        damage = Math.max(1, damage / 2);
        player.takeDamage(damage);

        StringBuilder sb = new StringBuilder();
        sb.append(enemy.getAttackMessage());
        sb.append(" 但由于你的防御姿态，只受到了 ").append(damage).append(" 点伤害。");
        if (player.isAlive()) {
            sb.append(" 剩余生命值：").append(player.getHealth()).append("。");
        }
        return sb.toString();
    }
}
