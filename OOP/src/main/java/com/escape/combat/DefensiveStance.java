package com.escape.combat;

import com.escape.entity.Player;
import com.escape.entity.Enemy;

public class DefensiveStance implements CombatStrategy {

    @Override
    public String execute(Player player, Enemy enemy) {
        // Defending: deal reduced damage but gain damage resistance for the turn
        int damage = player.getAttackPower() / 3;
        damage = Math.max(1, damage);
        enemy.takeDamage(damage);

        return "你采取防御姿态，谨慎地攻击——造成了 " + damage
                + " 点伤害（本回合受到的伤害减半）。";
    }
}
