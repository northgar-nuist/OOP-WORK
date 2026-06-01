package com.escape.combat;

import com.escape.entity.Player;
import com.escape.entity.Enemy;

public class NormalAttack implements CombatStrategy {

    @Override
    public String execute(Player player, Enemy enemy) {
        int damage = player.getAttackPower() + (int) (Math.random() * 10) - 5;
        damage = Math.max(1, damage);
        enemy.takeDamage(damage);

        StringBuilder sb = new StringBuilder();
        sb.append("你向").append(enemy.getName()).append("发起攻击");
        if (player.getWeapon() != null) {
            sb.append("（使用").append(player.getWeapon().getName()).append("）");
        }
        sb.append("——造成了 ").append(damage).append(" 点伤害。");
        if (enemy.isAlive()) {
            sb.append(" 敌人剩余生命值：").append(enemy.getHealth()).append("。");
        }
        return sb.toString();
    }
}
